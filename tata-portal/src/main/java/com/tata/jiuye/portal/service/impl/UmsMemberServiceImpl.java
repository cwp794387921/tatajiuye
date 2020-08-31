package com.tata.jiuye.portal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.*;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.domain.MemberDetails;
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

    private static final Logger log = LoggerFactory.getLogger(UmsMemberServiceImpl.class);
    @Resource
    private  PasswordEncoder passwordEncoder;
    @Resource
    private  JwtTokenUtil jwtTokenUtil;
    @Resource
    private  UmsMemberMapper memberMapper;
    @Resource
    private UmsMemberAndMemberMapper memberAndMemberMapper;
    @Resource
    private UmsMemberAndBranchMapper memberAndBranchMapper;
    @Resource
    private AcctInfoMapper acctInfoMapper;
    @Resource
    private  UmsMemberLevelMapper memberLevelMapper;
    @Resource
    private  UmsMemberCacheService memberCacheService;

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
    public String Wxlogin(String wxCode,String phone,String fatherId){
        String token = null;
        try {
            JSONObject result = GetWeiXinCode.getOpenId(wxCode);
            String accessToken = result.get("access_token").toString();
            String openId = result.get("openid").toString();
           // String openId="1111";
           // String accessToken="1111";
            //查询是否已有该用户
            UmsMember umsMember = memberMapper.selectByUsername(phone);
            if(umsMember!=null){
                //已注册 直接登陆
                log.info("===》用户已存在，直接登录");
                //绑定openid
                umsMember.setOpenId(openId);
                memberMapper.updateByPrimaryKey(umsMember);
            }else{
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
                //绑定关系
                String grandPaId;
                String parent;
                if(fatherId!=null){
                    log.info("邀请人手机号:"+fatherId);
                    UmsMember father = memberMapper.selectByUsername(fatherId);
                    UmsMemberAndMember fatherInfo=memberAndMemberMapper.selectByUsername(fatherId);
                    UmsMemberAndBranch fatherBranchInfo=memberAndBranchMapper.selectById(fatherId);
                    if(father==null||fatherInfo==null||fatherBranchInfo==null){
                        log.info("邀请人信息不存在");
                        return null;
                    }
                    grandPaId=fatherInfo.getFatherId();
                    if(fatherBranchInfo.getIsBranch()==0){
                        parent=fatherBranchInfo.getParent();
                    }else{
                        log.info("邀请人配送中心编号:"+fatherBranchInfo.getUserBarnch());
                        parent=fatherBranchInfo.getUserBarnch();
                    }
                }else{
                    log.info("未携带邀请码注册");
                    log.info("上级绑定到平台");
                    fatherId="00000000";
                    grandPaId="00000000";
                    parent="0000000000";
                }
                //生成会员关系表
                UmsMemberAndMember umsMemberAndMember=new UmsMemberAndMember();
                umsMemberAndMember.setUserName(phone);
                umsMemberAndMember.setCreateTime(new Date());
                umsMemberAndMember.setFatherId(fatherId);
                umsMemberAndMember.setGrandpaId(grandPaId);
                umsMemberAndMember.setRecommendId(fatherId);
                //生成会员配送中心关系表
                UmsMemberAndBranch umsMemberAndBranch=new UmsMemberAndBranch();
                umsMemberAndBranch.setIsBranch(0);
                umsMemberAndBranch.setParent(parent);
                umsMemberAndBranch.setUserName(phone);
                //生成会员账户信息表
                String acctId=phone+ GlobalConstants.ACCTITAIL2;
                AcctInfo acctInfo=new AcctInfo();
                acctInfo.setAcctId(acctId);
                acctInfo.setBalance(new BigDecimal(0));
                acctInfo.setMemberNo(phone);
                acctInfo.setEffDate(new Date());
                acctInfo.setInsertTime(new Date());
                acctInfo.setUpdateTime(new Date());
                acctInfo.setStatus(1);
                //插入会员信息
                memberMapper.insert(umsMember);
                memberAndMemberMapper.insert(umsMemberAndMember);
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

}
