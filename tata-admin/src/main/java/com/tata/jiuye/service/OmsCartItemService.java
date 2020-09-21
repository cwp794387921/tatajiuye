package com.tata.jiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.OmsCartItem;
import com.tata.jiuye.model.PmsProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OmsCartItemService extends IService<OmsCartItem> {

    @Transactional
    public void updateOmsCartItems(List<OmsCartItem> omsCartItems, PmsProduct product);


    public List<OmsCartItem> getByProductId(Long productId);
}
