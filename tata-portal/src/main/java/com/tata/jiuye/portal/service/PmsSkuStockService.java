package com.tata.jiuye.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.PmsSkuStock;
import org.springframework.transaction.annotation.Transactional;

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
     * 锁定库存
     * @param productId             待锁定库存的商品ID
     * @param lockQuintity          待锁定数量
     */
    @Transactional
    void lockStock(Long productId,Integer lockQuintity);

    /**
     * 增加库存
     * @param productId
     * @param addQuintity
     */
    @Transactional
    void addStock(Long productId,Integer addQuintity);

    /**
     * 减少库存
     * @param productId
     * @param subtractQuintity
     */
    @Transactional
    void subtractStock(Long productId,Integer subtractQuintity);
}
