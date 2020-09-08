package com.tata.jiuye.mapper;

import com.tata.jiuye.model.ChangeDistribution;
import com.tata.jiuye.model.ChangeDistributionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChangeDistributionMapper {
    long countByExample(ChangeDistributionExample example);

    int deleteByExample(ChangeDistributionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ChangeDistribution record);

    int insertSelective(ChangeDistribution record);

    List<ChangeDistribution> selectByExample(ChangeDistributionExample example);

    ChangeDistribution selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ChangeDistribution record, @Param("example") ChangeDistributionExample example);

    int updateByExample(@Param("record") ChangeDistribution record, @Param("example") ChangeDistributionExample example);

    int updateByPrimaryKeySelective(ChangeDistribution record);

    int updateByPrimaryKey(ChangeDistribution record);
}