package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.OmsOrder;
import com.tata.jiuye.model.OmsOrderExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OmsOrderMapper extends BaseMapper<OmsOrder> {

    long countByExample(OmsOrderExample example);

    int deleteByExample(OmsOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OmsOrder record);

    int insertSelective(OmsOrder record);

    List<OmsOrder> selectByExample(OmsOrderExample example);

    List<OmsOrder> queryList(Map<String,Object> params);

    Map<String,Object> queryOrderCount(Long id);

    OmsOrder selectByPrimaryKey(Long id);

    OmsOrder selectByOrderNum(String  orderNum);

    int updateByExampleSelective(@Param("record") OmsOrder record, @Param("example") OmsOrderExample example);

    int updateByExample(@Param("record") OmsOrder record, @Param("example") OmsOrderExample example);

    int updateByPrimaryKeySelective(OmsOrder record);

    int updateByPrimaryKey(OmsOrder record);
}