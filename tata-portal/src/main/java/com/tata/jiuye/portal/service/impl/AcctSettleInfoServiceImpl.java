package com.tata.jiuye.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.tata.jiuye.DTO.AcctSettleInfoResult;
import com.tata.jiuye.DTO.OmsOrderDetailDTO;
import com.tata.jiuye.DTO.TotalFlowQueryParam;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.AcctSettleInfoMapper;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.common.constant.StaticConstant;
import com.tata.jiuye.portal.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;


/**
 * 账户流水相关业务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AcctSettleInfoServiceImpl extends ServiceImpl<AcctSettleInfoMapper, AcctSettleInfo> implements AcctSettleInfoService {

    @Autowired
    private UmsMemberService umsMemberService;
    @Autowired
    private OmsOrderItemService omsOrderItemService;
    @Autowired
    private AcctInfoService acctInfoService;
    @Autowired
    private AcctSettleInfoMapper acctSettleInfoMapper;
    @Autowired
    private OmsPortalOrderService omsPortalOrderService;

    //上级配送中心分佣金额(当购买的商品为升级配送中心商品时,专用)
    private static BigDecimal DIRECT_SUPERIOR_DISTRIBUTION_CENTER_MEMBER_COMMISSION_AMMOUNT = new BigDecimal("1500");

    //上上级配送中心分佣金额(当购买的商品为升级配送中心商品时,专用)
    private static BigDecimal INDIRECT_SUPERIOR_DISTRIBUTION_CENTER_MEMBER_COMMISSION_AMMOUNT = new BigDecimal("500");
    @Override
    public void saveOrUpdateAcctSettleInfo(AcctSettleInfo acctSettleInfo){
        this.saveOrUpdate(acctSettleInfo);
    }

    @Override
    public void insertCommissionRecordFlow(UmsMember umsMember,String orderSn){
        log.info("----------------------执行分佣流水   开始----------------------");
        //获取邀请链条(消费人->直邀人 -> 间邀人)
        UmsMemberInviteRelation umsMemberInviteRelation = umsMemberService.getInvitationChainByMemberId(umsMember.getId());
        log.info("----------------------获取邀请链条  umsMemberInviteRelation : "+umsMemberInviteRelation);
        //有上级会员才继续执行分佣流程
        if(umsMemberInviteRelation != null){
            //找到账户信息
            Long parentMemberId = umsMemberInviteRelation.getFatherMemberId();
            log.info("----------------------直邀人用户ID : "+parentMemberId);
            Long grandpaMemberId = umsMemberInviteRelation.getGrandpaMemberId();
            log.info("----------------------间邀人用户ID : "+grandpaMemberId);
            //通过订单号获取订单-商品,取到其上的该商品本邀请人可以分多少金额
            List<OmsOrderItem> omsOrderItems = omsOrderItemService.getItemForOrderSn(orderSn);
            log.info("----------------------订单-商品 : "+omsOrderItems);
            for(OmsOrderItem omsOrderItem : omsOrderItems){
                //商品购买数量
                Integer productQuantity = omsOrderItem.getProductQuantity();
                //直邀分佣金额
                BigDecimal directPushAmount = omsOrderItem.getDirectPushAmount().multiply(BigDecimal.valueOf(productQuantity));
                //间邀分佣金额
                BigDecimal indirectPushAmount = omsOrderItem.getIndirectPushAmount().multiply(BigDecimal.valueOf(productQuantity));
                //当购买商品为升级为配送中心时
                if(omsOrderItem.getIfUpgradeDistributionCenterProduct() == 1){
                    Long memberId = umsMember.getId();
                    log.info("----------------------升级人的用户ID : "+memberId);
                    //上级配送中心ID
                    Long directSuperiorDistributionCenterMemberId = umsMemberService.getSuperiorDistributionCenterMemberIdNotOwner(memberId);
                    log.info("----------------------上级配送中心的用户ID : "+directSuperiorDistributionCenterMemberId);
                    //插入上级配送中心分佣
                    if(directSuperiorDistributionCenterMemberId != null){
                        insertFlow(directSuperiorDistributionCenterMemberId,DIRECT_SUPERIOR_DISTRIBUTION_CENTER_MEMBER_COMMISSION_AMMOUNT,orderSn,umsMember.getId(),StaticConstant.FLOW_TYPE_INCOME,
                                StaticConstant.FLOW_TYPE_DETAIL_INCOME_COMMISSION_INCOME,StaticConstant.ACCOUNT_TYPE_ORDINARY,null);
                        if(!directSuperiorDistributionCenterMemberId.equals(1L)){
                            Long indirectSuperiorDistributionCenterMemberId = umsMemberService.getSuperiorDistributionCenterMemberIdNotOwner(directSuperiorDistributionCenterMemberId);
                            log.info("----------------------上上级配送中心的用户ID : "+indirectSuperiorDistributionCenterMemberId);
                            //插入上上级配送中心分佣
                            if(indirectSuperiorDistributionCenterMemberId != null){
                                insertFlow(indirectSuperiorDistributionCenterMemberId,INDIRECT_SUPERIOR_DISTRIBUTION_CENTER_MEMBER_COMMISSION_AMMOUNT,orderSn,umsMember.getId(),StaticConstant.FLOW_TYPE_INCOME,
                                        StaticConstant.FLOW_TYPE_DETAIL_INCOME_COMMISSION_INCOME,StaticConstant.ACCOUNT_TYPE_ORDINARY,null);
                            }
                        }
                    }
                }
                else{
                    //获取直邀账户并增加余额
                    if(!directPushAmount.equals(BigDecimal.ZERO)){
                        log.info("-----------------执行正常商品直邀分佣 ");
                        insertFlow(parentMemberId,directPushAmount,orderSn,umsMember.getId(),StaticConstant.FLOW_TYPE_INCOME,
                                StaticConstant.FLOW_TYPE_DETAIL_INCOME_COMMISSION_INCOME,StaticConstant.ACCOUNT_TYPE_ORDINARY,null);
                    }
                    //获取间邀账户并增加余额
                    if(!indirectPushAmount.equals(BigDecimal.ZERO)){
                        log.info("-----------------执行正常商品间邀分佣 ");
                        insertFlow(grandpaMemberId,indirectPushAmount,orderSn,umsMember.getId(),StaticConstant.FLOW_TYPE_INCOME,
                                StaticConstant.FLOW_TYPE_DETAIL_INCOME_COMMISSION_INCOME,StaticConstant.ACCOUNT_TYPE_ORDINARY,null);
                    }
                }
            }
        }
        log.info("----------------------执行分佣流水   结束----------------------");
    }

    @Override
    public void insertFlow(Long directPushMemberId,BigDecimal changeAmount,String orderSn,Long sourceId,String flowType,String flowTypeDetail,String accountType,Long omsDistributionNo){
        log.info("----------------------插入资金变动流水   开始----------------------");
        AcctSettleInfo acctSettleInfo = acctInfoService.updateAcctInfoByAmount(directPushMemberId,changeAmount,flowType,accountType);
        Long acctId = acctSettleInfo.getAcctId();
        log.info("----------------------账户ID 为 "+acctId);
        BigDecimal beforBal = acctSettleInfo.getBeforBal();
        log.info("----------------------账户变更前余额 为 "+beforBal);
        BigDecimal afterBal = acctSettleInfo.getAfterBal();
        log.info("----------------------账户变更后余额 为 "+afterBal);
        log.info("----------------------账户变更的金额 为 "+changeAmount);
        if(!changeAmount.equals(BigDecimal.ZERO)){
            //插入传入的Member对应的邀流水表(直邀或间邀)
            insertAcctInfoChangeFlow(orderSn,acctId,beforBal,afterBal,changeAmount,sourceId,flowType,flowTypeDetail,omsDistributionNo);
        }
        log.info("----------------------插入资金变动流水   开始----------------------");
    }



    /**
     * 插入账户变更流水
     * @param orderSn                           订单号
     * @param acctId                            账户ID
     * @param beforBal                          账户变更前余额
     * @param afterBal                          账户变更后余额
     * @param changeAmount                      账户变更的金额
     * @param sourceId                          变更金额来源的ID(用户ID)
     * @param flowType                          流水类型
     * @param flowTypeDetail                    流水类型明细
     * @param omsDistributionNo                 配送/补货单号
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AcctSettleInfo insertAcctInfoChangeFlow(String orderSn,Long acctId,BigDecimal beforBal,BigDecimal afterBal,BigDecimal changeAmount,Long sourceId,String flowType,String flowTypeDetail,Long omsDistributionNo){
        log.info("----------------------插入一条账户变更记录   开始----------------------");
        //插入传入的Member对应的邀流水表(直邀或间邀)
        AcctSettleInfo acctSettleInfo = new AcctSettleInfo();
        acctSettleInfo.setOrderNo(orderSn);
        acctSettleInfo.setOmsDistributionNo(omsDistributionNo);
        acctSettleInfo.setAcctId(acctId);
        acctSettleInfo.setBeforBal(beforBal);
        acctSettleInfo.setAfterBal(afterBal);
        acctSettleInfo.setChangeAmount(changeAmount);
        acctSettleInfo.setFlowType(flowType);
        acctSettleInfo.setFlowTypeDetail(flowTypeDetail);
        acctSettleInfo.setSourceId(sourceId);
        acctSettleInfo.setInsertTime(new Date());
        this.saveOrUpdate(acctSettleInfo);
        log.info("----------------------插入的变更记录内容为 "+acctSettleInfo);
        log.info("----------------------插入一条账户变更记录   结束----------------------");
        return acctSettleInfo;
    }

    @Override
    public BigDecimal getTodayIncome(Long memberId){
        log.info("----------------------获取今日收入   开始----------------------");
        if(memberId == null){
            Asserts.fail("用户未登录");
        }
        log.info("----------------------参数用户ID "+memberId);
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atTime(00,00,00);
        Date startDate = Date.from(startDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
        LocalDateTime endDateTime = today.atTime(23, 59, 59);
        Date endDate = Date.from(endDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
        TotalFlowQueryParam param = new TotalFlowQueryParam();
        param.setMemberId(memberId);
        param.setFlowType(StaticConstant.FLOW_TYPE_INCOME);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        BigDecimal todayIncome = acctSettleInfoMapper.getIncome(param);
        log.info("----------------------今日收入 "+todayIncome);
        log.info("----------------------获取今日收入   结束----------------------");
        return todayIncome;
    }

    @Override
    public BigDecimal getTotalIncome(Long memberId){
        if(memberId == null){
            Asserts.fail("用户未登录");
        }
        TotalFlowQueryParam param = new TotalFlowQueryParam();
        param.setMemberId(memberId);
        param.setFlowType(StaticConstant.FLOW_TYPE_INCOME);
        return acctSettleInfoMapper.getIncome(param);
    }

    @Override
    public CommonPage<AcctSettleInfoResult> getBalanceAndFlow(Integer pageNum, Integer pageSize, Long acctId, String year, String month,String flowType){
        log.info("----------------------获取某个时间段明细   开始----------------------");
        if(acctId == null){
            Asserts.fail("用户未登录");
        }
        log.info("----------------------参数 页码 "+pageNum);
        log.info("----------------------参数 每页条数 "+pageSize);
        log.info("----------------------参数 账户ID "+acctId);
        log.info("----------------------参数 年份 "+year);
        log.info("----------------------参数 月份 "+month);
        log.info("----------------------参数 流水类型 "+flowType);
        PageHelper.startPage(pageNum,pageSize);
        List<AcctSettleInfoResult> acctSettleInfos = acctSettleInfoMapper.getIncomeFlow(acctId,year,month,flowType);
        CommonPage<AcctSettleInfoResult> commonPage = CommonPage.restPage(acctSettleInfos);
        log.info("----------------------获取某个时间段明细   开始----------------------");
        return commonPage;
    }

    @Override
    public void insertWithdrawExamineAcctSettleInfo(Long memberId,BigDecimal withdrawAmount,String accountType){
        log.info("----------------------插入提现流水,同时更新账户余额(审批通过时调用)   开始----------------------");
        log.info("----------------------参数 用户ID "+memberId);
        log.info("----------------------参数 提现金额 "+withdrawAmount);
        //1.获取账户信息
        AcctInfo acctInfo = acctInfoService.getAcctInfoByMemberId(memberId,accountType);
        BigDecimal lockAmount = acctInfo.getLockAmount();
        if(lockAmount == null){
            lockAmount = BigDecimal.ZERO;
        }
        BigDecimal actBalance = acctInfo.getBalance().subtract(lockAmount);
        log.info("----------------------参数 账户信息 "+acctInfo);
        AcctSettleInfo acctSettleInfo = acctInfoService.updateAcctInfoByAmount(memberId,withdrawAmount,StaticConstant.FLOW_TYPE_EXPENDITURE,accountType);
        if(acctInfo == null){
            Asserts.fail("该用户ID无法找到对应的账户信息");
        }
        Long acctId = acctSettleInfo.getAcctId();
        log.info("----------------------账户ID 为 "+acctId);
        BigDecimal beforBal = acctSettleInfo.getBeforBal();
        log.info("----------------------账户变更前余额 为 "+beforBal);
        BigDecimal afterBal = acctSettleInfo.getAfterBal();
        log.info("----------------------账户变更后余额 为 "+afterBal);
        //1.插入提现申请记录
        if(!withdrawAmount.equals(BigDecimal.ZERO)){
            log.info("----------------------执行账户流水插入");
            insertAcctInfoChangeFlow("",acctId,beforBal,afterBal,withdrawAmount,null,StaticConstant.FLOW_TYPE_EXPENDITURE,StaticConstant.FLOW_TYPE_DETAIL_EXPENDITURE_WITHDRAW,null);
        }
        log.info("----------------------插入提现流水,同时更新账户余额(审批通过时调用)   结束----------------------");
    }


    //获取明细详情
    @Override
    public OmsOrderDetailDTO getDetailedDetails(String orderNo){
        if(StringUtils.isEmpty(orderNo)){
            Asserts.fail("订单号为空");
        }
        OmsOrderDetailDTO resultDto = new OmsOrderDetailDTO();
        //1.通过订单号获取订单信息
        OmsOrder omsOrder = omsPortalOrderService.getOmsOrderByOrderSn(orderNo);
        if(omsOrder == null){
            Asserts.fail("查询不到订单号 "+orderNo+" 对应的订单信息");
        }
        resultDto.setOrderAmount(omsOrder.getPayAmount());
        if(3 == omsOrder.getStatus()){
            resultDto.setFlowStatus(StaticConstant.CREDITED);
        }
        else{
            resultDto.setFlowStatus(StaticConstant.TO_BE_CREDITED);
        }
        resultDto.setOrderNo(orderNo);
        //2.通过订单号获取商品详情
        List<OmsOrderItem> omsOrderItems = omsOrderItemService.getItemForOrderSn(orderNo);
        resultDto.setOrderItems(omsOrderItems);
        return  resultDto;
    }
}
