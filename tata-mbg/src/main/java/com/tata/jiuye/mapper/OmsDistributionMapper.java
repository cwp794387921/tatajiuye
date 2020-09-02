package com.tata.jiuye.mapper;

import com.tata.jiuye.model.OmsDistribution;
import com.tata.jiuye.model.OmsDistributionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OmsDistributionMapper {
    long countByExample(OmsDistributionExample example);

    int deleteByExample(OmsDistributionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OmsDistribution record);

    int insertSelective(OmsDistribution record);

    List<OmsDistribution> selectByExample(OmsDistributionExample example);

    List<OmsDistribution> queryList(Long id);

    OmsDistribution selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OmsDistribution record, @Param("example") OmsDistributionExample example);

    int updateByExample(@Param("record") OmsDistribution record, @Param("example") OmsDistributionExample example);

    int updateByPrimaryKeySelective(OmsDistribution record);

    int updateByPrimaryKey(OmsDistribution record);
}