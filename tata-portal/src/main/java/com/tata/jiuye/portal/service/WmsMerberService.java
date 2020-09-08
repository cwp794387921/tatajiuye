package com.tata.jiuye.portal.service;

import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.model.WmsMemberAreaDetail;

import java.util.List;

public interface WmsMerberService {

    JSONObject selectMerberInfo();

    List<WmsMemberAreaDetail> queryAllUser();

    void changeDistribution(Long changeId,Long orderId);

}
