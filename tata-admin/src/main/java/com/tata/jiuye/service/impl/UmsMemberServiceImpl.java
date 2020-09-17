package com.tata.jiuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.DTO.UmsMemberInfoByMemberIdResult;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.UmsMemberInviteRelationMapper;
import com.tata.jiuye.mapper.UmsMemberMapper;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.model.UmsMemberInviteRelation;
import com.tata.jiuye.service.UmsMemberLevelService;
import com.tata.jiuye.service.UmsMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Autowired
    private UmsMemberInviteRelationMapper umsMemberInviteRelationMapper;
    @Autowired
    private UmsMemberMapper memberMapper;
    @Autowired
    private UmsMemberLevelService umsMemberLevelService;
    @Value("${umsmemberlevelname.deliverycenter}")
    private String UMS_MEMBER_LEVEL_NAME_DELIVERYCENTER;
    @Value("${umsmemberlevelname.ordinarymember}")
    private String UMS_MEMBER_LEVEL_NAME_ORDINARYMEMBER;

    @Override
    public UmsMemberInfoByMemberIdResult getUmsInfoByMemberId(Long memberId) {
        if(memberId == null){
            Asserts.fail("用户ID为空");
        }
        UmsMember umsMember = this.getById(memberId);
        if(umsMember == null){
            Asserts.fail("找不到对应的用户信息");
        }
        UmsMemberInfoByMemberIdResult result = new UmsMemberInfoByMemberIdResult();
        BeanUtils.copyProperties(umsMember,result);
        UmsMemberInviteRelation umsMemberInviteRelation = umsMemberInviteRelationMapper.getByMemberId(memberId);
        if(umsMemberInviteRelation != null){
            Long fatherMemberId = umsMemberInviteRelation.getFatherMemberId();
            UmsMember fatherMember = this.getById(fatherMemberId);
            Long grandpaMemberId = umsMemberInviteRelation.getGrandpaMemberId();
            UmsMember grandpaMember = this.getById(grandpaMemberId);
            result.setFatherMemberName(fatherMember.getNickname());
            result.setGrandpaMemberName(grandpaMember.getNickname());
            Long distributionSuperiorMemberId = getSuperiorDistributionCenterMemberId(memberId);
            if(distributionSuperiorMemberId != null){
                UmsMember distributionSuperiorMember = this.getById(distributionSuperiorMemberId);
                if(distributionSuperiorMember != null){
                    result.setDistributionSuperiorName(distributionSuperiorMember.getNickname());
                }
            }
        }
        return result;
    }



    @Override
    public Long getSuperiorDistributionCenterMemberId(Long memberId){
        log.info("--------------------------获取所属配送中心用户ID   开始--------------------------");
        if(memberId == null){
            Asserts.fail("传入的用户ID为空");
        }
        log.info("memberID "+memberId);
        UmsMember umsMember = memberMapper.selectByPrimaryKey(memberId);
        log.info("用户信息 "+ umsMember);
        //通过用户ID查找用户信息,再用用户信息上的用户等级ID获取用户等级,是否为配送中心
        Boolean isDeliveryCenter = umsMemberLevelService.isSomeOneLevel(umsMember.getMemberLevelId(),UMS_MEMBER_LEVEL_NAME_DELIVERYCENTER);
        log.info("上级是否配送中心 "+ isDeliveryCenter);
        // 判断用户等级,若不为配送中心,则查找绑定关系表找到上级,再看其是否配送中心
        if(!isDeliveryCenter){
            //查找上级
            UmsMemberInviteRelation umsMemberInviteRelation = umsMemberInviteRelationMapper.getByMemberId(memberId);
            if(umsMemberInviteRelation == null){
                return null;
            }
            Long fatherMemberId = umsMemberInviteRelation.getFatherMemberId();
            //如果最高级是平台账户,且没有配送中心,则直接挂放平台
            if(fatherMemberId ==1){
                log.info("------------------最高级是平台直接返回");
                return 1L;
            }
            log.info("------------------递归查上级");
            return getSuperiorDistributionCenterMemberId(fatherMemberId);
        }
        else{
            Long deliveryCenterMemberId = umsMember.getId();
            log.info("--------------------------配送中心用户ID "+deliveryCenterMemberId);
            log.info("--------------------------获取所属配送中心用户ID   结束--------------------------");
            return deliveryCenterMemberId;
        }
    }
}
