package com.tata.jiuye.mapper;

import com.tata.jiuye.model.UmsBranch;
import com.tata.jiuye.model.UmsBranchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsBranchMapper {
    long countByExample(UmsBranchExample example);

    int deleteByExample(UmsBranchExample example);

    int deleteByPrimaryKey(String branchId);

    int insert(UmsBranch record);

    int insertSelective(UmsBranch record);

    List<UmsBranch> selectByExample(UmsBranchExample example);

    UmsBranch selectByPrimaryKey(String branchId);

    int updateByExampleSelective(@Param("record") UmsBranch record, @Param("example") UmsBranchExample example);

    int updateByExample(@Param("record") UmsBranch record, @Param("example") UmsBranchExample example);

    int updateByPrimaryKeySelective(UmsBranch record);

    int updateByPrimaryKey(UmsBranch record);
}