package com.tata.jiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.OmsCompanyAddress;

import java.util.List;

/**
 * 收货地址管Service
 *
 * @author lewis
 */
public interface OmsCompanyAddressService extends IService<OmsCompanyAddress> {

    /**
     * 获取全部收货地址
     */
    List<OmsCompanyAddress> list();
}
