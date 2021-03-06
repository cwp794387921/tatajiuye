package com.tata.jiuye.mapper;

import com.tata.jiuye.model.ReplenishableExamine;
import com.tata.jiuye.model.ReplenishableExamineExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ReplenishableExamineMapper {
    long countByExample(ReplenishableExamineExample example);

    int deleteByExample(ReplenishableExamineExample example);


    ReplenishableExamine selectByPrimaryKey(Long id);

    List<ReplenishableExamine> queryList(Map<String,Object> params);

    int insert(ReplenishableExamine record);

    int insertSelective(ReplenishableExamine record);

    List<ReplenishableExamine> selectByExample(ReplenishableExamineExample example);

    int updateByExampleSelective(@Param("record") ReplenishableExamine record, @Param("example") ReplenishableExamineExample example);

    int updateByExample(@Param("record") ReplenishableExamine record, @Param("example") ReplenishableExamineExample example);

    int updateByPrimaryKey(ReplenishableExamine record);
}