package com.tata.jiuye.mapper;

import com.tata.jiuye.model.WmsMemberCreditlineChange;
import com.tata.jiuye.model.WmsMemberCreditlineChangeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsMemberCreditlineChangeMapper {
    long countByExample(WmsMemberCreditlineChangeExample example);

    int deleteByExample(WmsMemberCreditlineChangeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WmsMemberCreditlineChange record);

    int insertSelective(WmsMemberCreditlineChange record);

    List<WmsMemberCreditlineChange> selectByExample(WmsMemberCreditlineChangeExample example);

    WmsMemberCreditlineChange selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WmsMemberCreditlineChange record, @Param("example") WmsMemberCreditlineChangeExample example);

    int updateByExample(@Param("record") WmsMemberCreditlineChange record, @Param("example") WmsMemberCreditlineChangeExample example);

    int updateByPrimaryKeySelective(WmsMemberCreditlineChange record);

    int updateByPrimaryKey(WmsMemberCreditlineChange record);
}