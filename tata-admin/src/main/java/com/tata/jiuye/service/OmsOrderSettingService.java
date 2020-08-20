package com.tata.jiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.OmsOrderSetting;

/**
 * 订单设置Service
 *
 * @author lewis
 */
public interface OmsOrderSettingService extends IService<OmsOrderSetting> {

    /**
     * 获取指定订单设置
     */
    OmsOrderSetting getItem(Long id);

    /**
     * 修改指定订单设置
     */
    int update(Long id, OmsOrderSetting orderSetting);
}
