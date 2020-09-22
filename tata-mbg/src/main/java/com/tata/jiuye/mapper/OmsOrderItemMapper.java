package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.OmsOrderItem;
import com.tata.jiuye.model.OmsOrderItemExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OmsOrderItemMapper extends BaseMapper<OmsOrderItem> {

    long countByExample(OmsOrderItemExample example);

    int deleteByExample(OmsOrderItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OmsOrderItem record);

    int insertSelective(OmsOrderItem record);

    List<OmsOrderItem> selectByExample(OmsOrderItemExample example);

    OmsOrderItem selectByPrimaryKey(Long id);

    OmsOrderItem selectByParams(Map<String,Object>params);

    List<OmsOrderItem> queryList(Map<String,Object>params);

    int updateByExampleSelective(@Param("record") OmsOrderItem record, @Param("example") OmsOrderItemExample example);

    int updateByExample(@Param("record") OmsOrderItem record, @Param("example") OmsOrderItemExample example);

    int updateByPrimaryKeySelective(OmsOrderItem record);

    int updateByPrimaryKey(OmsOrderItem record);

    /**
     * 通过订单号获取订单商品
     * @param orderNo
     * @return
     */
    List<OmsOrderItem> getByOrderNo(@Param("orderSn") String orderNo);
}
