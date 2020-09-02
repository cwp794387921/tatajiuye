package com.tata.jiuye.mapper;

import com.tata.jiuye.model.WmsMemberRoleRelation;
import com.tata.jiuye.model.WmsMemberRoleRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsMemberRoleRelationMapper {
    long countByExample(WmsMemberRoleRelationExample example);

    int deleteByExample(WmsMemberRoleRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WmsMemberRoleRelation record);

    int insertSelective(WmsMemberRoleRelation record);

    List<WmsMemberRoleRelation> selectByExample(WmsMemberRoleRelationExample example);

    WmsMemberRoleRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WmsMemberRoleRelation record, @Param("example") WmsMemberRoleRelationExample example);

    int updateByExample(@Param("record") WmsMemberRoleRelation record, @Param("example") WmsMemberRoleRelationExample example);

    int updateByPrimaryKeySelective(WmsMemberRoleRelation record);

    int updateByPrimaryKey(WmsMemberRoleRelation record);
}