package com.tata.jiuye.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.PmsSkuStockMapper;
import com.tata.jiuye.model.PmsSkuStock;
import com.tata.jiuye.model.PmsSkuStockExample;
import com.tata.jiuye.portal.common.constant.StaticConstant;
import com.tata.jiuye.portal.service.InventoryChangeRecordService;
import com.tata.jiuye.portal.service.PmsSkuStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 商品sku库存管理Service实现类
 * Created by macro on 2018/4/27.
 */
@Service
@RequiredArgsConstructor
public class PmsSkuStockServiceImpl extends ServiceImpl<PmsSkuStockMapper, PmsSkuStock> implements PmsSkuStockService {

    private final PmsSkuStockMapper skuStockMapper;
    private InventoryChangeRecordService inventoryChangeRecordService;
    @Override
    public List<PmsSkuStock> getList(Long pid, String keyword) {
        PmsSkuStockExample example = new PmsSkuStockExample();
        PmsSkuStockExample.Criteria criteria = example.createCriteria().andProductIdEqualTo(pid);
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andSkuCodeLike("%" + keyword + "%");
        }
        return skuStockMapper.selectByExample(example);
    }


    /**
     * 锁定下单商品的所有库存
     */
    @Override
    public void lockStock(Long productId,Integer lockQuintity) {
        if(productId == null){
            Asserts.fail("查找的商品ID不能为空");
        }
        PmsSkuStock skuStock = skuStockMapper.getByProductId(productId);
        skuStock.setLockStock(skuStock.getLockStock() + lockQuintity);
        skuStockMapper.updateByPrimaryKeySelective(skuStock);
    }

    //增加库存
    @Override
    public void addStock(Long productId,Integer addQuintity){
        if(productId == null){
            Asserts.fail("查找的商品ID不能为空");
        }
        PmsSkuStock skuStock = skuStockMapper.getByProductId(productId);
        Integer quantityBeforeChange = skuStock.getStock();
        Integer quantityAfterChange = quantityBeforeChange + addQuintity;
        skuStock.setStock(quantityAfterChange);
        skuStockMapper.updateByPrimaryKeySelective(skuStock);
        inventoryChangeRecordService.insertnventoryChangeRecord(addQuintity, StaticConstant.SKU_CHANGE_TYPE_WAREHOUSING,quantityAfterChange,quantityBeforeChange);
    }

    //减少库存
    @Override
    public void subtractStock(Long productId,Integer subtractQuintity){
        if(productId == null){
            Asserts.fail("查找的商品ID不能为空");
        }
        PmsSkuStock skuStock = skuStockMapper.getByProductId(productId);
        Integer quantityBeforeChange = skuStock.getStock();
        Integer quantityAfterChange = quantityBeforeChange - subtractQuintity;
        skuStock.setStock(quantityAfterChange);
        skuStockMapper.updateByPrimaryKeySelective(skuStock);
        inventoryChangeRecordService.insertnventoryChangeRecord(subtractQuintity, StaticConstant.SKU_CHANGE_TYPE_OUT_OF_STOCK,quantityAfterChange,quantityBeforeChange);
    }
}
