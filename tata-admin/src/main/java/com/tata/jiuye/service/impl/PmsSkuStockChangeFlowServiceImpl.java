package com.tata.jiuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.PmsSkuStockChangeFlowMapper;
import com.tata.jiuye.mapper.PmsSkuStockMapper;
import com.tata.jiuye.model.PmsSkuStock;
import com.tata.jiuye.model.PmsSkuStockChangeFlow;
import com.tata.jiuye.service.PmsSkuStockChangeFlowService;
import com.tata.jiuye.service.PmsSkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 库存变动流水记录表实现
 */
@Slf4j
@Service
public class PmsSkuStockChangeFlowServiceImpl extends ServiceImpl<PmsSkuStockChangeFlowMapper, PmsSkuStockChangeFlow> implements PmsSkuStockChangeFlowService {

    @Override
    public void insertPmsSkuStockChangeFlow(PmsSkuStockChangeFlow pmsSkuStockChangeFlow){
        log.info("---------------------------插入库存变动流水记录   开始---------------------------");
        if(pmsSkuStockChangeFlow.getPmsSkuStockId() == null){
            Asserts.fail("库存表ID不能为空");
        }
        if(pmsSkuStockChangeFlow.getChangeNum() == 0){
            return;
        }
        if(pmsSkuStockChangeFlow.getChangeWmsMemberId() == null){
            Asserts.fail("变动方配送中心ID不能为空");
        }
        this.saveOrUpdate(pmsSkuStockChangeFlow);
        log.info("流水 pmsSkuStockChangeFlow"+pmsSkuStockChangeFlow);
        log.info("---------------------------插入库存变动流水记录   结束---------------------------");
    }
}
