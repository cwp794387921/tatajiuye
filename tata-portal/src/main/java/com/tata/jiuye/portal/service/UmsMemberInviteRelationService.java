package com.tata.jiuye.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.DTO.DirectPerformanceResult;
import com.tata.jiuye.DTO.IndirectPerformanceResult;
import com.tata.jiuye.DTO.TotalPerformanceResult;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.model.UmsMemberInviteRelation;

import java.util.Map;


/**
 *  用户之间邀请关系
 * @author laich
 */
public interface UmsMemberInviteRelationService extends IService<UmsMemberInviteRelation> {

    /**
     * 获取指定用户直系客户业绩
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @return
     */
    CommonPage<DirectPerformanceResult> getDirectPerformance(Integer pageNum,Integer pageSize,Long memberId);

    /**
     * 获取指定用户间接邀请的客户业绩
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @return
     */
    CommonPage<IndirectPerformanceResult> getInDirectPerformance(Integer pageNum, Integer pageSize, Long memberId);

    /**
     * 获取团队销售总数量与团队销售总额
     * @param memberId
     * @return
     */
    Map<String,Object> getTotalPerformance(Long memberId);
}
