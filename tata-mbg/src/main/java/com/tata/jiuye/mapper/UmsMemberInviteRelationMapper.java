package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.DTO.UmsMemberAndMemberResult;
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
}
