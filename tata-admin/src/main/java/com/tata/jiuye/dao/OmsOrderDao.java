package com.tata.jiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.dto.OmsOrderDeliveryParam;
import com.tata.jiuye.dto.OmsOrderDetail;
import com.tata.jiuye.dto.OmsOrderQueryParam;
import com.tata.jiuye.model.OmsOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单自定义查询Mapper
 *
 * @author lewis
 */
public interface OmsOrderDao extends BaseMapper<OmsOrder> {

    /**
     * 条件查询订单
     */
    List<OmsOrder> getList(@Param("queryParam") OmsOrderQueryParam queryParam);

    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);

    /**
     * 获取订单详情
     */
    OmsOrderDetail getDetail(@Param("id") Long id);
}
