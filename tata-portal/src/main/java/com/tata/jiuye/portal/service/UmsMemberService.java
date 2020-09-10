package com.tata.jiuye.portal.service;

//import com.tata.jiuye.DTO.UmsMemberAndMemberResult;
import com.tata.jiuye.DTO.RegisteredMemberParam;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.model.UmsMemberInviteRelation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员管理Service
 * Created by macro on 2018/8/3.
 */
public interface UmsMemberService {
    /**
     * 根据用户名获取会员
     */
    UmsMember getByUsername(String username);

    /**
     * 根据会员编号获取会员
     */
    UmsMember getById(Long id);

    /**
     * 用户注册
     */
    @Transactional
    void register(String username, String password, String telephone, String authCode);

    /**
     * 生成验证码
     */
    String generateAuthCode(String telephone);

    /**
     * 修改密码
     */
    @Transactional
    void updatePassword(String telephone, String password, String authCode);

    /**
     * 获取当前登录会员
     */
    UmsMember getCurrentMember();

    /**
     * 根据会员id修改会员积分
     */
    @Transactional
    void updateIntegration(Long id,Integer integration);


    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 登录后获取token
     */
    String login(String username, String password);

    /**
     * 刷新token
     */
    String refreshToken(String token);

    /**
     * 微信小程序登陆后获取token
     */
    String Wxlogin(String wxCode);

    /**
     * 微信小程序注册并获取token
     * @param registeredMemberParam
     * @return
     */
    @Transactional
    String registeredMember(RegisteredMemberParam registeredMemberParam);

    /**
     * 升级用户会员等级
     * @param member                            待升级用户对象
     * @param umsMemberLevelName               升级的会员等级名称
     */
    @Transactional
    void updateUmsMemberLevel(UmsMember member,String umsMemberLevelName,String orderNo);

    /**
     * 通过被邀请人用户ID获取邀请链条
     * @param memberId                          待查询的用户ID
     * @return
     */
    UmsMemberInviteRelation getInvitationChainByMemberId(Long memberId);

    /**
     * 通过用户ID获取其上级配送中心用户的用户ID
     * @param memberId                          用户ID
     * @return
     */
    Long getSuperiorDistributionCenterMemberId(Long memberId);
}
