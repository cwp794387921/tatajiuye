package com.tata.jiuye.portal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.DTO.UmsMemberAndMemberResult;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.*;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.domain.MemberDetails;
import com.tata.jiuye.portal.service.OmsOrderItemService;
import com.tata.jiuye.portal.service.UmsMemberCacheService;
import com.tata.jiuye.portal.service.UmsMemberService;
import com.tata.jiuye.portal.util.GetWeiXinCode;
import com.tata.jiuye.portal.util.GlobalConstants;
import com.tata.jiuye.security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private UmsMemberAndBranchMapper memberAndBranchMapper;
    @Resource
    private AcctInfoMapper acctInfoMapper;
    @Resource
    private  UmsMemberLevelMapper memberLevelMapper;
    @Resource
    private  UmsMemberCacheService memberCacheService;
    @Resource
    private OmsOrderItemService omsOrderItemService;
    @Resource
    private UmsMemberLevelMapper umsMemberLevelMapper;
    @Resource
    private UmsMemberInviteRelationMapper umsMemberInviteRelationMapper;

    @Value("${redis.key.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

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
    public String Wxlogin(String wxCode,String phone,String invitorPhone){
        String token = null;
        try {
            JSONObject result = GetWeiXinCode.getOpenId(wxCode);
            if(result==null){
                return null;
            }
            String accessToken = result.get("access_token").toString();
            String openId = result.get("openid").toString();
           // String openId="1111";
           // String accessToken="1111";
            //查询是否已有该用户
            UmsMember umsMember = memberMapper.selectById(openId);
            if(umsMember!=null){
                //已注册 直接登陆
                log.info("===》用户已存在，直接登录");
            }else{
                if(phone==null){
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
                }
                log.info("===》开始注册流程");
                //没有该用户进行添加操作
                JSONObject object = GetWeiXinCode.getInfoUrlByAccessToken(accessToken,openId);//用户信息
                log.info("==========微信用户信息==========："+object);
                log.info("+++++++++++++++++微信头像+++++++++++++"+object.get("headimgurl"));
                log.info("+++++++++++++++++微信昵称+++++++++++++"+object.get("nickname"));
                umsMember = new UmsMember();
                umsMember.setUsername(phone);
                umsMember.setPhone(phone);
                umsMember.setPassword(passwordEncoder.encode("123456"));
                umsMember.setCreateTime(new Date());
                umsMember.setStatus(1);
                //umsMember.setIcon(object.get("headimgurl").toString());
                //umsMember.setGender((int) object.get("sex"));
                umsMember.setOpenId(openId);
                //获取默认会员等级并设置
                UmsMemberLevelExample levelExample = new UmsMemberLevelExample();
                levelExample.createCriteria().andDefaultStatusEqualTo(1);
                List<UmsMemberLevel> memberLevelList = memberLevelMapper.selectByExample(levelExample);
                if (!CollectionUtils.isEmpty(memberLevelList)) {
                    umsMember.setMemberLevelId(memberLevelList.get(0).getId());
                }
                //插入会员信息
                memberMapper.insert(umsMember);
                //绑定关系
                Long grandpaMemberId;
                Long parentMemberId;
                Long parentBranchId;
                if(invitorPhone!=null){
                    log.info("邀请人手机号:"+invitorPhone);
                    UmsMember fatherUmsMember = memberMapper.getUmsMemberByPhone(invitorPhone);
                    parentMemberId = fatherUmsMember.getId();
                    //邀请人用户层次上级与上上级(若无,则默认填平台,所以必定有)
                    UmsMemberInviteRelation fatherInfo = umsMemberInviteRelationMapper.getByMemberId(fatherUmsMember.getId());
                    //邀请人角色层次上级与上上级
                    UmsMemberAndBranch fatherBranchInfo = memberAndBranchMapper.getByMemberId(fatherUmsMember.getId());
                    if(fatherUmsMember == null||fatherInfo == null||fatherBranchInfo == null){
                        log.info("邀请人信息不存在");
                        return null;
                    }
                    grandpaMemberId=fatherInfo.getFatherMemberId();
                    if(fatherBranchInfo.getIsBranch() == 0){
                        parentBranchId=fatherBranchInfo.getParent();
                    }else{
                        log.info("邀请人配送中心编号:"+fatherBranchInfo.getUserBarnchId());
                        parentBranchId=fatherBranchInfo.getUserBarnchId();
                    }
                }else{
                    log.info("未携带邀请码注册");
                    log.info("上级绑定到平台");
                    invitorPhone="00000000000";
                    grandpaMemberId=0L;
                    parentMemberId=0L;
                    parentBranchId=0L;
                }
                //生成会员关系表
                UmsMemberInviteRelation umsMemberInviteRelation=new UmsMemberInviteRelation();
                umsMemberInviteRelation.setCreateTime(new Date());
                umsMemberInviteRelation.setFatherMemberId(parentMemberId);
                umsMemberInviteRelation.setGrandpaMemberId(grandpaMemberId);
                umsMemberInviteRelation.setMemberId(umsMember.getId());
                //生成会员配送中心关系表
                UmsMemberAndBranch umsMemberAndBranch=new UmsMemberAndBranch();
                umsMemberAndBranch.setIsBranch(0);
                umsMemberAndBranch.setParent(parentBranchId);
                umsMemberAndBranch.setUserName(phone);
                //生成会员账户信息表
                String acctId=phone+ GlobalConstants.ACCTITAIL2;
                AcctInfo acctInfo=new AcctInfo();
                //acctInfo.setAcctId(acctId);
                acctInfo.setBalance(BigDecimal.ZERO);
                acctInfo.setMemberId(umsMember.getId());
                acctInfo.setEffDate(new Date());
                acctInfo.setInsertTime(new Date());
                acctInfo.setUpdateTime(new Date());
                acctInfo.setStatus(1);

                umsMemberInviteRelationMapper.insert(umsMemberInviteRelation);
                memberAndBranchMapper.insert(umsMemberAndBranch);
                acctInfoMapper.insert(acctInfo);
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

}
