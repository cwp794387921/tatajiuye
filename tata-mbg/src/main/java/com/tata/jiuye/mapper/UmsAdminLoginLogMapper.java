package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.UmsAdminLoginLog;
import com.tata.jiuye.model.UmsAdminLoginLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsAdminLoginLogMapper extends BaseMapper<UmsAdminLoginLog> {

    long countByExample(UmsAdminLoginLogExample example);

    int deleteByExample(UmsAdminLoginLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsAdminLoginLog record);

    int insertSelective(UmsAdminLoginLog record);

    List<UmsAdminLoginLog> selectByExample(UmsAdminLoginLogExample example);

    UmsAdminLoginLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsAdminLoginLog record, @Param("example") UmsAdminLoginLogExample example);

    int updateByExample(@Param("record") UmsAdminLoginLog record, @Param("example") UmsAdminLoginLogExample example);

    int updateByPrimaryKeySelective(UmsAdminLoginLog record);

    int updateByPrimaryKey(UmsAdminLoginLog record);
}