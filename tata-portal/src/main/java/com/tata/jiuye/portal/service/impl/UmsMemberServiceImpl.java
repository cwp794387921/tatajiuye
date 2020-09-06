package com.tata.jiuye.portal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.DTO.RegisteredMemberParam;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.common.service.RedisService;
import com.tata.jiuye.mapper.AcctInfoMapper;
import com.tata.jiuye.mapper.UmsMemberInviteRelationMapper;
import com.tata.jiuye.mapper.UmsMemberLevelMapper;
import com.tata.jiuye.mapper.UmsMemberMapper;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.common.constant.StaticConstant;
import com.tata.jiuye.portal.domain.MemberDetails;
import com.tata.jiuye.portal.service.UmsMemberCacheService;
import com.tata.jiuye.portal.service.UmsMemberLevelService;
import com.tata.jiuye.portal.service.UmsMemberService;
import com.tata.jiuye.portal.util.GetWeiXinCode;
import com.tata.jiuye.portal.util.GlobalConstants;
import com.tata.jiuye.portal.util.HttpRequest;
import com.tata.jiuye.security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 会员管理Service实现类
 * Created by macro on 2018/8/3.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UmsMemberServiceImpl implements UmsMemberService {
    @Resource
    private  PasswordEncoder passwordEncoder;
    @Resource
    private  JwtTokenUtil jwtTokenUtil;
    @Resource
    private  UmsMemberMapper memberMapper;
    @Resource
    private AcctInfoMapper acctInfoMapper;
    @Resource
    private  UmsMemberLevelMapper memberLevelMapper;
    @Resource
    private  UmsMemberCacheService memberCacheService;
    @Resource
    private UmsMemberLevelMapper umsMemberLevelMapper;
    @Resource
    private UmsMemberInviteRelationMapper umsMemberInviteRelationMapper;
    @Resource
    private UmsMemberLevelService umsMemberLevelService;
    @Resource
    private RedisService redisService;
    @Value("${redis.key.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;
    @Value("${umsmemberlevelname.deliverycenter}")
    private String UMS_MEMBER_LEVEL_NAME_DELIVERYCENTER;
    @Value("${auth.wechat.sessionHost}")
    private String WECHAT_SESSION_HOST;
    //小程序APPID
    @Value("${auth.wechat.appId}")
    private String WECHAT_APP_ID;
    //小程序秘钥
    @Value("${auth.wechat.secret}")
    private String WECHAT_SECRET;
    @Override
    public UmsMember getByUsername(String username) {
        UmsMember member = memberCacheService.getMember(username);
        if (member != null) return member;
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsMember> memberList = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(memberList)) {
            member = memberList.get(0);
            memberCacheService.setMember(member);
            return member;
        }
        return null;
    }

    @Override
    public UmsMember getById(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public void register(String username, String password, String telephone, String authCode) {
        //验证验证码
        if (!verifyAuthCode(authCode, telephone)) {
            Asserts.fail("验证码错误");
        }
        //查询是否已有该用户
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        example.or(example.createCriteria().andPhoneEqualTo(telephone));
        List<UmsMember> umsMembers = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(umsMembers)) {
            Asserts.fail("该用户已经存在");
        }
        //没有该用户进行添加操作
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(username);
        umsMember.setPhone(telephone);
        umsMember.setPassword(passwordEncoder.encode(password));
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        //获取默认会员等级并设置
        UmsMemberLevelExample levelExample = new UmsMemberLevelExample();
        levelExample.createCriteria().andDefaultStatusEqualTo(1);
        List<UmsMemberLevel> memberLevelList = memberLevelMapper.selectByExample(levelExample);
        if (!CollectionUtils.isEmpty(memberLevelList)) {
            umsMember.setMemberLevelId(memberLevelList.get(0).getId());
        }
        memberMapper.insert(umsMember);
        umsMember.setPassword(null);
    }

    @Override
    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        memberCacheService.setAuthCode(telephone, sb.toString());
        return sb.toString();
    }

    @Override
    public void updatePassword(String telephone, String password, String authCode) {
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andPhoneEqualTo(telephone);
        List<UmsMember> memberList = memberMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(memberList)) {
            Asserts.fail("该账号不存在");
        }
        //验证验证码
        if (!verifyAuthCode(authCode, telephone)) {
            Asserts.fail("验证码错误");
        }
        UmsMember umsMember = memberList.get(0);
        umsMember.setPassword(passwordEncoder.encode(password));
        memberMapper.updateByPrimaryKeySelective(umsMember);
        memberCacheService.delMember(umsMember.getId());
    }

    @Override
    public UmsMember getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
        return memberDetails.getUmsMember();
    }

    @Override
    public void updateIntegration(Long id, Integer integration) {
        UmsMember record = new UmsMember();
        record.setId(id);
        record.setIntegration(integration);
        memberMapper.updateByPrimaryKeySelective(record);
        memberCacheService.delMember(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsMember member = getByUsername(username);
        if (member != null) {
            return new MemberDetails(member);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

    //对输入的验证码进行校验
    private boolean verifyAuthCode(String authCode, String telephone) {
        if (StringUtils.isEmpty(authCode)) {
            return false;
        }
        String realAuthCode = memberCacheService.getAuthCode(telephone);
        return authCode.equals(realAuthCode);
    }

    @Override
    public String Wxlogin(String wxCode){
        String token = null;
        try {
            JSONObject result = GetWeiXinCode.getOpenId(wxCode);
            if(result==null){
                return null;
            }
            String openId = result.get("openid").toString();
            log.info("openId为 "+openId);
            //查询是否已有该用户
            UmsMember umsMember = memberMapper.selectById(openId);
            if(umsMember!=null){
                //已注册 直接登陆
                log.info("===》用户已存在，直接登录");
            }else{
                log.info("==>需要验证手机号");
                return "1";
                /*if(phone==null){
                    //openid未绑定,验证手机号注册或绑定
                    log.info("==>需要验证手机号");
                    return "1";
                }else{
                    UmsMember umsMember1 = memberMapper.selectByUsername(phone);
                    if(umsMember1!=null){
                        //手机号已绑定
                        log.info("===>["+phone+"]该手机号已绑定另外一个账号");
                        return "2";
                    }
                    //openid未绑定 手机号未注册  开始注册流程
                }*/
            }
            UserDetails userDetails=new MemberDetails(umsMember);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }catch (Exception e){
            log.info(e.getMessage());
            throw new RuntimeException();
        }
        return token;
    }

    //注册
    @Override
    public String registeredMember(RegisteredMemberParam registeredMemberParam){
        log.info("===》开始注册流程");
        if(StringUtils.isEmpty(registeredMemberParam.getWxCode())){
            Asserts.fail("微信小程序CODE为空");
        }
        if(StringUtils.isEmpty(registeredMemberParam.getPhone())){
            Asserts.fail("用户手机号为空");
        }
        if(StringUtils.isEmpty(registeredMemberParam.getUserInfoJson())){
            Asserts.fail("用户信息为空");
        }
        //没有该用户进行添加操作
        JSONObject result = GetWeiXinCode.getOpenId(registeredMemberParam.getWxCode());
        if(result==null){
            Asserts.fail("获取openId失败");
        }
        String openId = result.get("openid").toString();
        log.info("openId 为 "+openId);
        //JSONObject object = GetWeiXinCode.getInfoUrlByAccessToken(accessToken,openId);//用户信息
        JSONObject object = JSONObject.parseObject(registeredMemberParam.getUserInfoJson());
        log.info("==========微信用户信息==========："+object);
        //log.info("+++++++++++++++++微信头像+++++++++++++"+object.get("headimgurl"));
        //log.info("+++++++++++++++++微信昵称+++++++++++++"+object.get("nickname"));
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(registeredMemberParam.getPhone());
        umsMember.setPhone(registeredMemberParam.getPhone());
        umsMember.setPassword(passwordEncoder.encode("123456"));
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        umsMember.setNickname(object.getString("nickName"));
        umsMember.setIcon(object.getString("avatarUrl"));
        umsMember.setCity(object.getString("city"));
        umsMember.setGender(object.getInteger("gender"));
        umsMember.setOpenid(openId);
        //获取默认会员等级并设置
        UmsMemberLevelExample levelExample = new UmsMemberLevelExample();
        levelExample.createCriteria().andDefaultStatusEqualTo(1);
        List<UmsMemberLevel> memberLevelList = memberLevelMapper.selectByExample(levelExample);
        if (!CollectionUtils.isEmpty(memberLevelList)) {
            umsMember.setMemberLevelId(memberLevelList.get(0).getId());
        }
        String selfInviteCode = generateInviteCode();
        umsMember.setInviteCode(selfInviteCode);
        //插入会员信息
        memberMapper.insert(umsMember);
        //绑定关系
        Long grandpaMemberId;
        Long parentMemberId;
        String inviteCode = registeredMemberParam.getInviteCode();
        if(!StringUtils.isEmpty(inviteCode)){
            log.info("邀请人的邀请码:"+inviteCode);
            UmsMember fatherUmsMember = memberMapper.getUmsMemberByInviteCode(inviteCode);
            if(fatherUmsMember == null){
                log.info("邀请人信息不存在");
                return null;
            }
            //邀请人用户层次上级与上上级(若无,则默认填平台,所以必定有)
            UmsMemberInviteRelation fatherInfo = umsMemberInviteRelationMapper.getByMemberId(fatherUmsMember.getId());
            //邀请人角色角色等级(是否配送中心)
            //UmsMemberLevel umsMemberLevel = memberLevelMapper.selectByPrimaryKey(fatherUmsMember.getMemberLevelId());
            parentMemberId = fatherUmsMember.getId();
            grandpaMemberId=fatherInfo.getFatherMemberId();
        }else{
            log.info("未携带邀请码注册");
            log.info("上级绑定到平台");
            grandpaMemberId=0L;
            parentMemberId=0L;
        }
        //生成会员关系表
        UmsMemberInviteRelation umsMemberInviteRelation=new UmsMemberInviteRelation();
        umsMemberInviteRelation.setCreateTime(new Date());
        umsMemberInviteRelation.setFatherMemberId(parentMemberId);
        umsMemberInviteRelation.setGrandpaMemberId(grandpaMemberId);
        umsMemberInviteRelation.setMemberId(umsMember.getId());
        //生成会员账户信息表
        //String acctId=phone+ GlobalConstants.ACCTITAIL2;
        AcctInfo acctInfo=new AcctInfo();
        //acctInfo.setAcctId(acctId);
        acctInfo.setBalance(BigDecimal.ZERO);
        acctInfo.setMemberId(umsMember.getId());
        acctInfo.setEffDate(new Date());
        acctInfo.setInsertTime(new Date());
        acctInfo.setUpdateTime(new Date());
        acctInfo.setStatus(StaticConstant.INTEGER_STATUS_TAKE_EFFECT);
        acctInfo.setAcctType(StaticConstant.ACCT_TYPE_ORDINARYMEMBERACCOUNT);
        umsMemberInviteRelationMapper.insert(umsMemberInviteRelation);
        acctInfoMapper.insert(acctInfo);
        UserDetails userDetails=new MemberDetails(umsMember);
        String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }


    @Override
    public void updateUmsMemberLevel(UmsMember member,String umsMemberLevelName){
        //判断购买的订单商品是否为升级VIP需购买的商品,是则升级会员等级为VIP(且要VIP才能继续购买别的商品)
        UmsMemberLevelExample umsMemberLevelExample = new UmsMemberLevelExample();
        UmsMemberLevelExample.Criteria criteria = umsMemberLevelExample.createCriteria();
        criteria.andNameEqualTo(umsMemberLevelName);
        List<UmsMemberLevel> umsMemberLevels = umsMemberLevelMapper.selectByExample(umsMemberLevelExample);
        if(CollectionUtils.isEmpty(umsMemberLevels)){
            Asserts.fail("传入的会员等级名称找不到对应的记录");
        }
        UmsMemberLevel umsMemberLevel = umsMemberLevels.get(0);
        member.setMemberLevelId(umsMemberLevel.getId());
        memberMapper.updateByPrimaryKeySelective(member);
        memberCacheService.setMember(member);
    }

    @Override
    public UmsMemberInviteRelation getInvitationChainByMemberId(Long memberId){
        log.info("------------------------- 通过被邀请人用户ID获取邀请链条 开始 -------------------------");
        log.info("------------------------- 参数 用户ID : "+ memberId);
        if(memberId == null){
            Asserts.fail("用户ID为空");
        }
        UmsMemberInviteRelation umsMemberInviteRelation = umsMemberInviteRelationMapper.getByMemberId(memberId);
        log.info("------------------------- 邀请链条为 : "+ umsMemberInviteRelation);
        log.info("------------------------- 通过被邀请人用户ID获取邀请链条 结束 -------------------------");
        return umsMemberInviteRelation;
    }


    @Override
    public Long getSuperiorDistributionCenterMemberId(Long memberId){
        if(memberId == null){
            Asserts.fail("传入的用户ID为空");
        }
        UmsMember umsMember = memberMapper.selectByPrimaryKey(memberId);
        //通过用户ID查找用户信息,再用用户信息上的用户等级ID获取用户等级,是否为配送中心
        Boolean isDeliveryCenter = umsMemberLevelService.isSomeOneLevel(umsMember.getMemberLevelId(),UMS_MEMBER_LEVEL_NAME_DELIVERYCENTER);
        // 判断用户等级,若不为配送中心,则查找绑定关系表找到上级,再看其是否配送中心
        if(!isDeliveryCenter){
            //查找上级
            UmsMemberInviteRelation umsMemberInviteRelation = umsMemberInviteRelationMapper.getByMemberId(memberId);
            if(umsMemberInviteRelation == null){
                return null;
            }
            Long fatherMemberId = umsMemberInviteRelation.getFatherMemberId();
            //如果最高级是平台账户,且没有配送中心,则直接挂放平台
            if(fatherMemberId ==0){
                return 0L;
            }
            getSuperiorDistributionCenterMemberId(fatherMemberId);
        }
        return umsMember.getId();
    }

    //第一步,小程序端通过wxCode请求后台,后台利用wxCode获取openId和session_key
    public String getOpenIdAndSeesionKey(String wxCode){
        String result = HttpRequest.sendGet(WECHAT_SESSION_HOST,
                "appid=" + WECHAT_APP_ID +
                        "&secret="+ WECHAT_SECRET +
                        "&js_code="+ wxCode + //前端传来的code
                        "&grant_type=authorization_code");
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.containsKey("errcode")) {
            Asserts.fail("code无效");
        }
        String jsonObjectStr = jsonObject.toJSONString();
        String openId = jsonObject.get("openid").toString();
        if (StringUtils.isEmpty(openId)) {
            Asserts.fail("openid为空");
        }

        //查询是否已有该用户
        UmsMember umsMember = memberMapper.getUmsMemberByOpenId(openId);
        if(umsMember != null){
            log.info("--------------已存在,则返回session_key给前端让前端申请授权");
        }
        return jsonObjectStr;
    }


    /**
     * 生成邀请码6位以上自增id
     */
    private String generateInviteCode() {
        StringBuilder sb = new StringBuilder();
        //获取秒数
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        //获取毫秒数
        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String date = milliSecond.toString();
        String key =  date;
        Long increment = redisService.incr(key, 1);
        sb.append(date);
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }
}
