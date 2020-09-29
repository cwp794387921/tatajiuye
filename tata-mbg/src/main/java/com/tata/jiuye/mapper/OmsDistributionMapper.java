package com.tata.jiuye.mapper;

import com.tata.jiuye.DTO.DeliveryInfo;
import com.tata.jiuye.model.OmsDistribution;
import com.tata.jiuye.model.OmsDistributionExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OmsDistributionMapper {
    long countByExample(OmsDistributionExample example);

    int deleteByExample(OmsDistributionExample example);


    int deleteByPrimaryKey(Long id);

    int insert(OmsDistribution record);

    int insertSelective(OmsDistribution record);

    List<OmsDistribution> selectByExample(OmsDistributionExample example);

    List<OmsDistribution> queryList(OmsDistribution distribution);

    List<OmsDistribution> queryCHList(Map<String,Object> params);

    List<OmsDistribution> queryPSList(Map<String,Object> params);

    OmsDistribution selectByPrimaryKey(Long id);

    OmsDistribution queryDistributionDetail(OmsDistribution distribution);

    List<OmsDistribution> queryDistributionDetailList(Map<String,Object> params);

    OmsDistribution selectByParams(OmsDistribution distribution);

    int updateByExampleSelective(@Param("record") OmsDistribution record, @Param("example") OmsDistributionExample example);

    int updateByExample(@Param("record") OmsDistribution record, @Param("example") OmsDistributionExample example);

    int updateByPrimaryKeySelective(OmsDistribution record);

    int updateByPrimaryKey(OmsDistribution record);

    /**
     * 通过订单号获取送货人信息
     * @param orderNo
     * @return
     */
    DeliveryInfo getDeliveryInfoByOrderSn(@Param("orderNo")String orderNo);

    /**
     * 通过订单号获取配送单
     * @param orderNo
     * @return
     */
    OmsDistribution getByOrderSn(@Param("orderNo")String orderNo);
}
