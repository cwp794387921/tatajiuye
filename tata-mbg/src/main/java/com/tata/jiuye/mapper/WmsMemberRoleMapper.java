package com.tata.jiuye.mapper;

import com.tata.jiuye.model.WmsMemberRole;
import com.tata.jiuye.model.WmsMemberRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsMemberRoleMapper {
    long countByExample(WmsMemberRoleExample example);

    int deleteByExample(WmsMemberRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WmsMemberRole record);

    int insertSelective(WmsMemberRole record);

    List<WmsMemberRole> selectByExample(WmsMemberRoleExample example);

    WmsMemberRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WmsMemberRole record, @Param("example") WmsMemberRoleExample example);

    int updateByExample(@Param("record") WmsMemberRole record, @Param("example") WmsMemberRoleExample example);

    int updateByPrimaryKeySelective(WmsMemberRole record);

    int updateByPrimaryKey(WmsMemberRole record);
}