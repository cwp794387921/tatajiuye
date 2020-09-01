package com.tata.jiuye.portal.service;

import com.tata.jiuye.model.OmsOrderItem;

import java.util.List;

public interface OmsOrderItemService {


    /**
     * 通过订单号 获取该订单的商品列表
     * @param orderSn           订单号
     * @return
     */
    List<OmsOrderItem> getItemForOrderSn(String orderSn);
}
