package com.tata.jiuye.mapper;

import com.tata.jiuye.model.OmsDistributionItem;
import com.tata.jiuye.model.OmsDistributionItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OmsDistributionItemMapper {
    long countByExample(OmsDistributionItemExample example);

    int deleteByExample(OmsDistributionItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OmsDistributionItem record);

    int insertSelective(OmsDistributionItem record);

    List<OmsDistributionItem> selectByExample(OmsDistributionItemExample example);

    OmsDistributionItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OmsDistributionItem record, @Param("example") OmsDistributionItemExample example);

    int updateByExample(@Param("record") OmsDistributionItem record, @Param("example") OmsDistributionItemExample example);

    int updateByPrimaryKeySelective(OmsDistributionItem record);

    int updateByPrimaryKey(OmsDistributionItem record);
}