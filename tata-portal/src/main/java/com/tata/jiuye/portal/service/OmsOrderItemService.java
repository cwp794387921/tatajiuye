package com.tata.jiuye.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.OmsOrderItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OmsOrderItemService extends IService<OmsOrderItem> {


    /**
     * 通过订单号 获取该订单的商品列表
     * @param orderSn           订单号
     * @return
     */
    List<OmsOrderItem> getItemForOrderSn(String orderSn);

    /**
     * 批量保存(带事务回滚)
     * @param orderItems
     */
    @Transactional
    void saveOrUpdateOmsOrderItemsBatch(List<OmsOrderItem> orderItems);
}
