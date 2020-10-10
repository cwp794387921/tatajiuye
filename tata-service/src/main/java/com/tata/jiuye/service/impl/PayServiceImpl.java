package com.tata.jiuye.service.impl;

import com.tata.jiuye.mapper.OmsOrderMapper;
import com.tata.jiuye.model.OmsOrder;
import com.tata.jiuye.service.PayService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class PayServiceImpl implements PayService {
    @Resource
    private OmsOrderMapper orderMapper;

    @Override
    public void refundApply(String orderNum,String refundMoney){
        Map<String,Object> params=new HashMap<>();
        params.put("orderNum",orderNum);
        OmsOrder omsOrder = orderMapper.selectByOrderNum(params);
        System.out.println(omsOrder);

    }

}
