package com.tata.jiuye.portal.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WmsAreaService extends IService<WmsArea> {

    /**
     * 插入配送中心地址信息
     * @param omsOrder
     * @param wmsMemberId
     */
    @Transactional
    void insertWmsArea(OmsOrder omsOrder,Long wmsMemberId);


    WmsArea getByMemberId(Long wmsMemberId);

    @Transactional
    void updateWmsArea(WmsArea wmsArea);

    @Transactional
    void delByWmsMemberId(Long wmsMemberId);
}
