package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.PmsSkuStockChangeFlow;
import com.tata.jiuye.model.PmsSkuStockChangeFlowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsSkuStockChangeFlowMapper extends BaseMapper<PmsSkuStockChangeFlow> {
    long countByExample(PmsSkuStockChangeFlowExample example);

    int deleteByExample(PmsSkuStockChangeFlowExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsSkuStockChangeFlow record);

    int insertSelective(PmsSkuStockChangeFlow record);

    List<PmsSkuStockChangeFlow> selectByExample(PmsSkuStockChangeFlowExample example);

    PmsSkuStockChangeFlow selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsSkuStockChangeFlow record, @Param("example") PmsSkuStockChangeFlowExample example);

    int updateByExample(@Param("record") PmsSkuStockChangeFlow record, @Param("example") PmsSkuStockChangeFlowExample example);

    int updateByPrimaryKeySelective(PmsSkuStockChangeFlow record);

    int updateByPrimaryKey(PmsSkuStockChangeFlow record);
}
