package com.tata.jiuye.portal.service;

import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.model.PmsProduct;
import com.tata.jiuye.model.WmsMemberAreaDetail;
import com.tata.jiuye.model.ProductParams;

import java.util.List;

public interface WmsMerberService {

    JSONObject selectMerberInfo();

    List<WmsMemberAreaDetail> queryAllUser();

    void changeDistribution(Long changeId,Long orderId);

    void acceptOrder(Long orderId);

    void arriveOrder(Long orderId);

    void replenishable(List<ProductParams>  params);

    List<PmsProduct>  queryReplenishableList();

}
