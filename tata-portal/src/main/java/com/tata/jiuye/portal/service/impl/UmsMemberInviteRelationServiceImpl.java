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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 用户邀请关系(绑定邀请关系业务实现)
 * @author laich
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UmsMemberInviteRelationServiceImpl extends ServiceImpl<UmsMemberInviteRelationMapper, UmsMemberInviteRelation> implements UmsMemberInviteRelationService {
    @Resource
    private UmsMemberInviteRelationMapper umsMemberInviteRelationMapper;


    @Override
    public CommonPage<DirectPerformanceResult> getDirectPerformance(Integer pageNum,Integer pageSize,Long memberId){
        if(memberId == null){
            Asserts.fail("用户ID为空");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<DirectPerformanceResult> results = new ArrayList<>();
        List<DirectPerformanceResult> directPerformanceResults = umsMemberInviteRelationMapper.getDirectPerformance(memberId);
        if(CollectionUtils.isEmpty(directPerformanceResults)){
            results = umsMemberInviteRelationMapper.getDirectPerformanceWhenAllOrderNumNull(memberId);
        }
        else{
            results = umsMemberInviteRelationMapper.getDirectPerformanceWhenAllOrderNumNull(memberId);
            List<DirectPerformanceResult> temporarys = new ArrayList<>();
            for (DirectPerformanceResult directPerformanceResult : directPerformanceResults){
                DirectPerformanceResult temporary = new DirectPerformanceResult();
                BeanUtils.copyProperties(directPerformanceResult,temporary);
                temporary.setOrderCount(0);
                temporary.setTotalPayAmount(BigDecimal.ZERO);
                results.remove(temporary);
            }
            for (DirectPerformanceResult directPerformanceResult : directPerformanceResults){
                results.add(directPerformanceResult);
            }
            Collections.sort(results);
        }
        CommonPage<DirectPerformanceResult> resultCommonPage = CommonPage.restPage(results);
        return resultCommonPage;
    }


    @Override
    public CommonPage<IndirectPerformanceResult> getInDirectPerformance(Integer pageNum, Integer pageSize, Long memberId){
        if(memberId == null){
            Asserts.fail("用户ID为空");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<IndirectPerformanceResult> results = new ArrayList<>();
        List<IndirectPerformanceResult> indirectPerformanceResults = umsMemberInviteRelationMapper.getIndirectPerformance(memberId);
        if(CollectionUtils.isEmpty(indirectPerformanceResults)){
            results = umsMemberInviteRelationMapper.getIndirectPerformanceWhenAllOrderNumNull(memberId);
        }
        else{
            results = umsMemberInviteRelationMapper.getIndirectPerformanceWhenAllOrderNumNull(memberId);
            for (IndirectPerformanceResult indirectPerformanceResult : indirectPerformanceResults){
                IndirectPerformanceResult temporary = new IndirectPerformanceResult();
                BeanUtils.copyProperties(indirectPerformanceResult,temporary);
                temporary.setOrderCount(0);
                temporary.setTotalPayAmount(BigDecimal.ZERO);
                results.remove(temporary);
            }
            for(IndirectPerformanceResult indirectPerformanceResult : indirectPerformanceResults){
                results.add(indirectPerformanceResult);
            }
            Collections.sort(results);
        }
        CommonPage<IndirectPerformanceResult> resultCommonPage = CommonPage.restPage(results);
        return resultCommonPage;
    }



    @Override
    public Map<String,Object> getTotalPerformance(Long memberId){
        if(memberId == null){
            Asserts.fail("用户ID为空");
        }
        return umsMemberInviteRelationMapper.getTotalPerformance(memberId);
    }
}
