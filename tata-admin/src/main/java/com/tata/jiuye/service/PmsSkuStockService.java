package com.tata.jiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.PmsSkuStock;

import java.util.List;

/**
 * sku商品库存管理Service
 * Created by macro on 2018/4/27.
 */
public interface PmsSkuStockService extends IService<PmsSkuStock> {

    /**
     * 根据产品id和skuCode模糊搜索
     */
    List<PmsSkuStock> getList(Long pid, String keyword);

    /**
     * 批量更新商品库存信息
     */
    int update(Long pid, List<PmsSkuStock> skuStockList);

    /**
     * 变更库存数量
     * @param pmsSkuStockId                     库存ID
     * @param changeNum                         变更数量
     * @param operationType                     变更类型(WAREHOUSING->入库,OUTOFSTOCK->出库)
     */
    void changeSkuStockNum(Long pmsSkuStockId,Integer changeNum,String operationType);
}
