package com.tata.jiuye.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.tata.jiuye.DTO.DirectPerformanceResult;
import com.tata.jiuye.DTO.IndirectPerformanceResult;
import com.tata.jiuye.DTO.TotalPerformanceResult;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.UmsMemberInviteRelationMapper;
import com.tata.jiuye.model.UmsMemberInviteRelation;
import com.tata.jiuye.portal.service.UmsMemberInviteRelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户邀请关系(绑定邀请关系业务实现)
 * @author laich
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UmsMemberInviteRelationServiceImpl extends ServiceImpl<UmsMemberInviteRelationMapper, UmsMemberInviteRelation> implements UmsMemberInviteRelationService {
    @Autowired
    private UmsMemberInviteRelationMapper umsMemberInviteRelationMapper;


    @Override
    public CommonPage<DirectPerformanceResult> getDirectPerformance(Integer pageNum,Integer pageSize,Long memberId){
        if(memberId == null){
            Asserts.fail("用户ID为空");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<DirectPerformanceResult> directPerformanceResults = umsMemberInviteRelationMapper.getDirectPerformance(memberId);
        if(CollectionUtils.isEmpty(directPerformanceResults)){
            directPerformanceResults = umsMemberInviteRelationMapper.getDirectPerformanceWhenAllOrderNumNull(memberId);
        }
        CommonPage<DirectPerformanceResult> resultCommonPage = CommonPage.restPage(directPerformanceResults);
        return resultCommonPage;
    }


    @Override
    public CommonPage<IndirectPerformanceResult> getInDirectPerformance(Integer pageNum, Integer pageSize, Long memberId){
        if(memberId == null){
            Asserts.fail("用户ID为空");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<IndirectPerformanceResult> indirectPerformanceResults = umsMemberInviteRelationMapper.getIndirectPerformance(memberId);
        if(CollectionUtils.isEmpty(indirectPerformanceResults)){
            indirectPerformanceResults = umsMemberInviteRelationMapper.getIndirectPerformanceWhenAllOrderNumNull(memberId);
        }
        CommonPage<IndirectPerformanceResult> resultCommonPage = CommonPage.restPage(indirectPerformanceResults);
        return resultCommonPage;
    }



    @Override
    public TotalPerformanceResult getTotalPerformance(Long memberId){
        if(memberId == null){
            Asserts.fail("用户ID为空");
        }
        return umsMemberInviteRelationMapper.getTotalPerformance(memberId);
    }
}
