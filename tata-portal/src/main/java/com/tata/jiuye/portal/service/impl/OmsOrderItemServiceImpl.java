package com.tata.jiuye.portal.service.impl;

import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.OmsOrderItemMapper;
import com.tata.jiuye.model.OmsOrderItem;
import com.tata.jiuye.model.OmsOrderItemExample;
import com.tata.jiuye.portal.service.OmsOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 订单商品相关业务实现
 */
@Slf4j
@Service
public class OmsOrderItemServiceImpl implements OmsOrderItemService {
    @Autowired
    private OmsOrderItemMapper omsOrderItemMapper;

    @Override
    public List<OmsOrderItem> getItemForOrderSn(String orderSn){
        if(StringUtils.isEmpty(orderSn)){
            Asserts.fail("订单号不能为空");
        }
        OmsOrderItemExample omsOrderItemExample = new OmsOrderItemExample();
        OmsOrderItemExample.Criteria criteria = omsOrderItemExample.createCriteria();
        criteria.andOrderSnEqualTo(orderSn);
        return omsOrderItemMapper.selectByExample(omsOrderItemExample);
    }


}
