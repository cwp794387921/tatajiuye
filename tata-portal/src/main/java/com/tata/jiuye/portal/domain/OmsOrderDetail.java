package com.tata.jiuye.portal.domain;

import com.tata.jiuye.DTO.DeliveryInfo;
import com.tata.jiuye.model.OmsOrder;
import com.tata.jiuye.model.OmsOrderItem;

import java.util.List;

/**
 * 包含订单商品信息的订单详情
 * Created by macro on 2018/9/4.
 */
public class OmsOrderDetail extends OmsOrder {
    private List<OmsOrderItem> orderItemList;

    //送货人信息
    private DeliveryInfo deliveryInfo;


    public List<OmsOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OmsOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

}
