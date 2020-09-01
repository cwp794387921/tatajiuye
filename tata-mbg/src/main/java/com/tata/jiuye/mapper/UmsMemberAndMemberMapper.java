package com.tata.jiuye.mapper;

import com.tata.jiuye.model.UmsMemberAndMember;
import com.tata.jiuye.model.UmsMemberAndMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsMemberAndMemberMapper {
    long countByExample(UmsMemberAndMemberExample example);

    int deleteByExample(UmsMemberAndMemberExample example);

    int insert(UmsMemberAndMember record);

    int insertSelective(UmsMemberAndMember record);

    UmsMemberAndMember selectByUsername(String userName);

    List<UmsMemberAndMember> selectByExample(UmsMemberAndMemberExample example);

    int updateByExampleSelective(@Param("record") UmsMemberAndMember record, @Param("example") UmsMemberAndMemberExample example);

    int updateByExample(@Param("record") UmsMemberAndMember record, @Param("example") UmsMemberAndMemberExample example);
}