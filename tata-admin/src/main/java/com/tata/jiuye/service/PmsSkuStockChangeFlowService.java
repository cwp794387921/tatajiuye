package com.tata.jiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.PmsSkuStockChangeFlow;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存变动流水记录相关业务
 */
public interface PmsSkuStockChangeFlowService extends IService<PmsSkuStockChangeFlow> {


    /**
     * 插入库存变动流水
     * @param pmsSkuStockChangeFlow
     */
    @Transactional
    void insertPmsSkuStockChangeFlow(PmsSkuStockChangeFlow pmsSkuStockChangeFlow);
}
