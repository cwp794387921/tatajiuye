package com.tata.jiuye.mapper;

import com.tata.jiuye.model.UmsMemberAndBranch;
import com.tata.jiuye.model.UmsMemberAndBranchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsMemberAndBranchMapper {
    long countByExample(UmsMemberAndBranchExample example);

    int deleteByExample(UmsMemberAndBranchExample example);

    int insert(UmsMemberAndBranch record);

    UmsMemberAndBranch selectById(String userName);

    int insertSelective(UmsMemberAndBranch record);

    List<UmsMemberAndBranch> selectByExample(UmsMemberAndBranchExample example);

    int updateByExampleSelective(@Param("record") UmsMemberAndBranch record, @Param("example") UmsMemberAndBranchExample example);

    int updateByExample(@Param("record") UmsMemberAndBranch record, @Param("example") UmsMemberAndBranchExample example);
}