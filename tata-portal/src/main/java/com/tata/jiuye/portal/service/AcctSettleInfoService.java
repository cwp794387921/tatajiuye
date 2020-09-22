package com.tata.jiuye.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.DTO.AcctSettleInfoResult;
import com.tata.jiuye.DTO.OmsOrderDetailDTO;
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
     * @param umsMember                     用户信息
     * @param orderSn                       订单号
     */
    void insertCommissionRecordFlow(UmsMember umsMember, String orderSn);

    /**
     * 获取今日收入
     * @param memberId                      用户ID
     * @return
     */
    BigDecimal getTodayIncome(Long memberId);

    /**
     * 获取总收入
     * @param memberId                      用户ID
     * @return
     */
    BigDecimal getTotalIncome(Long memberId);


    /**
     * 获取某个时间段明细
     * @param memberId                      用户ID
     * @param year                          年份
     * @param month                         月份
     * @param flowType                      流水类型
     * @return
     */
    CommonPage<AcctSettleInfoResult> getBalanceAndFlow(Integer pageNum, Integer pageSize, Long memberId, String year, String month, String flowType);


    /**
     * 插入提现流水,同时更新账户余额(审批通过时调用)
     * @param memberId                      用户ID
     * @param withdrawAmount               提现金额
     */
    void insertWithdrawExamineAcctSettleInfo(Long memberId,BigDecimal withdrawAmount,String accountType);


    /**
     * 通过订单号获取订单详情
     * @param orderNo
     * @return
     */
    OmsOrderDetailDTO getDetailedDetails(String orderNo);
}
