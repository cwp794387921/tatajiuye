package com.tata.jiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.PmsSkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义商品SKU管理Mapper
 *
 * @author lewis
 */
public interface PmsSkuStockDao extends BaseMapper<PmsSkuStock> {

    /**
     * 批量插入操作
     */
    int insertList(@Param("list")List<PmsSkuStock> skuStockList);

    /**
     * 批量插入或替换操作
     */
    int replaceList(@Param("list")List<PmsSkuStock> skuStockList);
}
