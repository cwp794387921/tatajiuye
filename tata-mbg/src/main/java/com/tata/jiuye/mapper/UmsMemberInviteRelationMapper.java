package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.DTO.DirectPerformanceResult;
import com.tata.jiuye.DTO.IndirectPerformanceResult;
import com.tata.jiuye.DTO.TotalPerformanceResult;
import com.tata.jiuye.model.UmsMemberInviteRelation;
import com.tata.jiuye.model.UmsMemberInviteRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsMemberInviteRelationMapper extends BaseMapper<UmsMemberInviteRelation> {
    long countByExample(UmsMemberInviteRelationExample example);

    int deleteByExample(UmsMemberInviteRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsMemberInviteRelation record);

    int insertSelective(UmsMemberInviteRelation record);

    List<UmsMemberInviteRelation> selectByExample(UmsMemberInviteRelationExample example);

    UmsMemberInviteRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsMemberInviteRelation record, @Param("example") UmsMemberInviteRelationExample example);

    int updateByExample(@Param("record") UmsMemberInviteRelation record, @Param("example") UmsMemberInviteRelationExample example);

    int updateByPrimaryKeySelective(UmsMemberInviteRelation record);

    int updateByPrimaryKey(UmsMemberInviteRelation record);

    UmsMemberInviteRelation getByMemberId(@Param("memberId") Long memberId);

    /**
     * 获取直系客户业绩
     * @param memberId
     * @return
     */
    List<DirectPerformanceResult> getDirectPerformance(@Param("fatherMemberId") Long memberId);


    /**
     * 获取直系客户业绩(当销售总额与总销量为空时)
     * @param memberId
     * @return
     */
    List<DirectPerformanceResult> getDirectPerformanceWhenAllOrderNumNull(@Param("fatherMemberId") Long memberId);

    /**
     * 获取间接邀请客户业绩
     * @param memberId
     * @return
     */
    List<IndirectPerformanceResult> getIndirectPerformance(@Param("grandpaMemberId") Long memberId);


    /**
     * 获取间接邀请客户业绩(当销售总额与总销量为空时)
     * @param memberId
     * @return
     */
    List<IndirectPerformanceResult> getIndirectPerformanceWhenAllOrderNumNull(@Param("grandpaMemberId") Long memberId);


    /**
     * 获取团队销售总额与团队销售总数量
     * @param memberId
     * @return
     */
    TotalPerformanceResult getTotalPerformance(@Param("memberId") Long memberId);
}
