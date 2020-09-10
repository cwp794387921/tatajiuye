package com.tata.jiuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.constant.StaticConstant;
import com.tata.jiuye.dao.PmsSkuStockDao;
import com.tata.jiuye.mapper.PmsSkuStockMapper;
import com.tata.jiuye.model.PmsSkuStock;
import com.tata.jiuye.model.PmsSkuStockChangeFlow;
import com.tata.jiuye.model.PmsSkuStockExample;
import com.tata.jiuye.service.PmsSkuStockChangeFlowService;
import com.tata.jiuye.service.PmsSkuStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 商品sku库存管理Service实现类
 * Created by macro on 2018/4/27.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PmsSkuStockServiceImpl extends ServiceImpl<PmsSkuStockMapper, PmsSkuStock> implements PmsSkuStockService {

    private final PmsSkuStockDao skuStockDao;
    private final PmsSkuStockMapper skuStockMapper;

    @Autowired
    private PmsSkuStockChangeFlowService pmsSkuStockChangeFlowService;

    @Override
    public List<PmsSkuStock> getList(Long pid, String keyword) {
        PmsSkuStockExample example = new PmsSkuStockExample();
        PmsSkuStockExample.Criteria criteria = example.createCriteria();
        if(pid != null){
            criteria.andProductIdEqualTo(pid);
        }
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andSkuCodeLike("%" + keyword + "%");
        }
        return skuStockMapper.selectByExample(example);
    }

    @Override
    public int update(Long pid, List<PmsSkuStock> skuStockList) {
        return skuStockDao.replaceList(skuStockList);
    }



    @Override
    public void changeSkuStockNum(Long pmsSkuStockId,Integer changeNum,String operationType){
        log.info("---------------------------变更库存   开始---------------------------");
        if(pmsSkuStockId == null){
            Asserts.fail("库存表ID不能为空");
        }
        log.info("---------------------------参数 库存ID "+pmsSkuStockId);
        log.info("---------------------------参数 变更数量 "+changeNum);
        log.info("---------------------------参数 变更类型 "+operationType);
        PmsSkuStock pmsSkuStock = skuStockMapper.selectByPrimaryKey(pmsSkuStockId);
        log.info("---------------------------变更前库存实体 "+pmsSkuStock);
        Integer quantityBeforeChange = pmsSkuStock.getStock();
        Integer quantityAfterChange = 0;
        PmsSkuStockChangeFlow pmsSkuStockChangeFlow = new PmsSkuStockChangeFlow();
        //入库(加库存)
        if(StaticConstant.SKU_CHANGE_TYPE_WAREHOUSING.equals(operationType)){
            pmsSkuStock.setStock(quantityBeforeChange + changeNum);
            quantityAfterChange = pmsSkuStock.getStock();
            pmsSkuStockChangeFlow.setChangeType(StaticConstant.SKU_CHANGE_TYPE_WAREHOUSING);
        }
        //出库
        else{
            pmsSkuStock.setStock(quantityBeforeChange - changeNum);
            quantityAfterChange = pmsSkuStock.getStock();
            pmsSkuStockChangeFlow.setChangeType(StaticConstant.SKU_CHANGE_TYPE_OUT_OF_STOCK);
        }
        this.saveOrUpdate(pmsSkuStock);
        log.info("---------------------------变更后库存实体 "+pmsSkuStock);
        pmsSkuStockChangeFlow.setChangeNum(changeNum);
        pmsSkuStockChangeFlow.setChangeWmsMemberId(pmsSkuStock.getWmsMemberId());
        pmsSkuStockChangeFlow.setCreateTime(new Date());
        pmsSkuStockChangeFlow.setPmsSkuStockId(pmsSkuStock.getId());
        pmsSkuStockChangeFlow.setQuantityAfterChange(quantityAfterChange);
        pmsSkuStockChangeFlow.setQuantityBeforeChange(quantityBeforeChange);
        pmsSkuStockChangeFlowService.insertPmsSkuStockChangeFlow(pmsSkuStockChangeFlow);
        log.info("---------------------------库存变更记录表 "+pmsSkuStockChangeFlow);
        log.info("---------------------------变更库存   结束---------------------------");
    }
}
