package com.tata.jiuye.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.model.AcctSettleInfo;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.portal.DTO.BalanceFlowResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface AcctSettleInfoService extends IService<AcctSettleInfo> {

    /**
     *  新增/修改 账户流水记录表
     * @param acctSettleInfo
     */
    @Transactional
    void saveOrUpdateAcctSettleInfo(AcctSettleInfo acctSettleInfo);

    /**
     * 执行分佣(分享)流水
     * @param umsMember
     * @param orderSn
     */
    void insertCommissionRecordFlow(UmsMember umsMember, String orderSn);

    /**
     * 获取今日收入
     * @param memberId
     * @return
     */
    BigDecimal getTodayIncome(Long memberId);

    /**
     * 获取总收入
     * @param memberId
     * @return
     */
    BigDecimal getTotalIncome(Long memberId);


    /**
     * 获取某时间段内的余额明细
     * @param memberId
     * @param year
     * @param month
     * @return
     */
    CommonPage getBalanceAndFlow(Integer pageNum,Integer pageSize,Long memberId, String year, String month,String flowType);
}
