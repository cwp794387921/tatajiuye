package com.tata.jiuye.portal.service;

import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.model.PmsProduct;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.model.WmsMemberAreaDetail;
import com.tata.jiuye.model.ProductParams;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WmsMemberService {

    JSONObject selectMerberInfo();

    List<WmsMemberAreaDetail> queryAllUser();

    void changeDistribution(Long changeId,Long orderId);

    void acceptOrder(Long orderId);

    void arriveOrder(Long orderId);

    void replenishable(List<ProductParams>  params);

    void replenishableCheck(Long id,List<String> imgs);

    List<PmsProduct>  queryReplenishableList();

    @Transactional
    void insertWmsMember(UmsMember umsMember);
}
