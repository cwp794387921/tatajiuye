package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.PmsProductAttributeValue;
import com.tata.jiuye.model.PmsProductAttributeValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductAttributeValueMapper extends BaseMapper<PmsProductAttributeValue> {

    long countByExample(PmsProductAttributeValueExample example);

    int deleteByExample(PmsProductAttributeValueExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductAttributeValue record);

    int insertSelective(PmsProductAttributeValue record);

    List<PmsProductAttributeValue> selectByExample(PmsProductAttributeValueExample example);

    PmsProductAttributeValue selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProductAttributeValue record, @Param("example") PmsProductAttributeValueExample example);

    int updateByExample(@Param("record") PmsProductAttributeValue record, @Param("example") PmsProductAttributeValueExample example);

    int updateByPrimaryKeySelective(PmsProductAttributeValue record);

    int updateByPrimaryKey(PmsProductAttributeValue record);
}