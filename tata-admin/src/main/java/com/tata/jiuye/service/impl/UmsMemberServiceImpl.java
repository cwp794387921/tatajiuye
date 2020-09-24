package com.tata.jiuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.DTO.UmsMemberInfoByMemberIdResult;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.UmsMemberInviteRelationMapper;
import com.tata.jiuye.mapper.UmsMemberMapper;
import com.tata.jiuye.mapper.WmsMemberMapper;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.model.UmsMemberInviteRelation;
import com.tata.jiuye.model.WmsMember;
import com.tata.jiuye.service.UmsMemberLevelService;
import com.tata.jiuye.service.UmsMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Autowired
    private UmsMemberInviteRelationMapper umsMemberInviteRelationMapper;
    @Autowired
    private UmsMemberMapper memberMapper;
    @Autowired
    private WmsMemberMapper wmsMemberMapper;

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


    @Override
    public void changeSuperior(Long memberId,Long targetMemberId){
        if(memberId == null){
            Asserts.fail("换绑人用户ID为空");
        }
        if(targetMemberId == null){
            Asserts.fail("换绑目标用户ID为空");
        }
        //1.通过换绑人用户ID,找到该条换绑关系
        UmsMemberInviteRelation relation = umsMemberInviteRelationMapper.getByMemberId(memberId);
        //2.通过换绑目标用户ID找到该用户的直邀上级
        UmsMemberInviteRelation targetRelation = umsMemberInviteRelationMapper.getByMemberId(targetMemberId);
        if(targetRelation == null){
            Asserts.fail("换绑目标用户未找到自身上级");
        }
        Long targetGrandpaMemberId = targetRelation.getFatherMemberId();
        //3.修改1中的直邀上级为 换绑目标用户ID,间邀上级为换绑目标用户直邀上级的用户ID
        if(relation == null){
            //如果原邀请关系为空,则直接新增
            UmsMemberInviteRelation theRelation = new UmsMemberInviteRelation();
            theRelation.setMemberId(memberId);
            theRelation.setGrandpaMemberId(targetGrandpaMemberId);
            theRelation.setFatherMemberId(targetMemberId);
            theRelation.setCreateTime(new Date());
            theRelation.setUpdateTime(new Date());
            addTarget(theRelation);
        }
        else{
            //若不为空,则更新
            relation.setUpdateTime(new Date());
            relation.setFatherMemberId(targetMemberId);
            relation.setGrandpaMemberId(targetGrandpaMemberId);
            updateTarget(relation);
        }
    }

    //降级
    @Override
    public void downgrade(Long memberId){
        if(memberId == null){
            Asserts.fail("待降级用户ID不能为空");
        }
        log.info("-------------参数 memberId : "+memberId);
        //1.将用户等级下降为3
        UmsMember member = memberMapper.selectByPrimaryKey(memberId);
        log.info("-------------member : "+member);
        if(member == null){
            Asserts.fail("找不到该用户ID对应的用户");
        }
        if(!member.getMemberLevelId().equals(2L)){
            Asserts.fail("非配送中心不可降级");
        }
        member.setMemberLevelId(3L);
        updateMember(member);
        //2.将wms_member账户状态置为不可用
        WmsMember wmsMember = wmsMemberMapper.getAvailableByMemberId(memberId);
        if(wmsMember != null){
            wmsMember.setUpdateTime(new Date());
            wmsMember.setStatus(0);
            wmsMember.setCreditLine(BigDecimal.ZERO);
            updateWmsMember(wmsMember);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateWmsMember(WmsMember wmsMember){
        wmsMemberMapper.updateByPrimaryKeySelective(wmsMember);
    }



    @Transactional(rollbackFor = Exception.class)
    public void updateMember(UmsMember umsMember){
        memberMapper.updateByPrimaryKeySelective(umsMember);
    }


    @Transactional(rollbackFor = Exception.class)
    public void updateTarget(UmsMemberInviteRelation relation){
        umsMemberInviteRelationMapper.updateByPrimaryKeySelective(relation);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addTarget(UmsMemberInviteRelation relation){
        umsMemberInviteRelationMapper.insertSelective(relation);
    }
}
