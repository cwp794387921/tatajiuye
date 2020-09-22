package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.DTO.AcctSettleInfoResult;
import com.tata.jiuye.DTO.TotalFlowQueryParam;
import com.tata.jiuye.model.AcctSettleInfo;
import com.tata.jiuye.model.AcctSettleInfoExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AcctSettleInfoMapper extends BaseMapper<AcctSettleInfo> {
    long countByExample(AcctSettleInfoExample example);

    int deleteByExample(AcctSettleInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AcctSettleInfo record);

    int insertSelective(AcctSettleInfo record);

    List<AcctSettleInfo> selectByExample(AcctSettleInfoExample example);

    AcctSettleInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AcctSettleInfo record, @Param("example") AcctSettleInfoExample example);

    int updateByExample(@Param("record") AcctSettleInfo record, @Param("example") AcctSettleInfoExample example);

    int updateByPrimaryKeySelective(AcctSettleInfo record);

    int updateByPrimaryKey(AcctSettleInfo record);

    /**
     * 获取指定时间段的指定类型流水总额
     * @return
     */
    BigDecimal getIncome(@Param("sumParam") TotalFlowQueryParam totalFlowQueryParam);

    /**
     * 获取某时间段内的余额明细
     * @param acctId          用户ID
     * @param year               年份
     * @param month              月份
     * @return
     */
    List<AcctSettleInfoResult> getIncomeFlow(@Param("acctId")Long acctId, @Param("year") String year, @Param("month") String month, @Param("flowType") String flowType);
}
