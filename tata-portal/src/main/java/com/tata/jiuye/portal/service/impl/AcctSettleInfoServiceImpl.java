package com.tata.jiuye.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.mapper.AcctSettleInfoMapper;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.common.constant.StaticConstant;
import com.tata.jiuye.portal.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


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
    @Override
    public void saveOrUpdateAcctSettleInfo(AcctSettleInfo acctSettleInfo){
        this.saveOrUpdate(acctSettleInfo);
    }

    //执行分佣(分享)流水
    public void insertCommissionRecordFlow(UmsMember umsMember,String orderSn){
        log.info("----------------------执行分佣流水   开始----------------------");
        //获取邀请链条(消费人->直邀人 -> 间邀人)
        UmsMemberInviteRelation umsMemberInviteRelation = umsMemberService.getInvitationChainByMemberId(umsMember.getId());
        //有上级会员才继续执行分佣流程
        if(umsMemberInviteRelation != null){
            //找到账户信息
            Long parentMemberId = umsMemberInviteRelation.getFatherMemberId();
            Long grandpaMemberId = umsMemberInviteRelation.getGrandpaMemberId();
            //通过订单号获取订单-商品,取到其上的该商品本邀请人可以分多少金额
            List<OmsOrderItem> omsOrderItems = omsOrderItemService.getItemForOrderSn(orderSn);
            for(OmsOrderItem omsOrderItem : omsOrderItems){
                //商品购买数量
                Integer productQuantity = omsOrderItem.getProductQuantity();
                //直邀分佣金额
                BigDecimal directPushAmount = omsOrderItem.getDirectPushAmount().multiply(BigDecimal.valueOf(productQuantity));
                //间邀分佣金额
                BigDecimal indirectPushAmount = omsOrderItem.getIndirectPushAmount().multiply(BigDecimal.valueOf(productQuantity));
                //获取直邀账户并增加余额
                insertCommissionFlow(parentMemberId,directPushAmount,orderSn,umsMember.getId());
                //获取间邀账户并增加余额
                insertCommissionFlow(grandpaMemberId,indirectPushAmount,orderSn,umsMember.getId());
            }
        }
        log.info("----------------------执行分佣流水   结束----------------------");
    }


    //插入分佣流水
    public void insertCommissionFlow(Long directPushMemberId,BigDecimal changeAmount,String orderSn,Long sourceId){
        Map<String,Object> resultMap = acctInfoService.updateAcctInfoByAmount(directPushMemberId,changeAmount,StaticConstant.FLOW_TYPE_INCOME);
        AcctInfo acctInfo = (AcctInfo) resultMap.get("acctInfo");
        BigDecimal beforBal = (BigDecimal) resultMap.get("beforBal");
        BigDecimal afterBal = (BigDecimal) resultMap.get("afterBal");
        //插入传入的Member对应的邀流水表(直邀或间邀)
        insertAcctInfoChangeFlow(orderSn,acctInfo.getId(),beforBal,afterBal,changeAmount,sourceId);
    }



    //插入一条账户变更记录
    @Transactional(rollbackFor = Exception.class)
    public void insertAcctInfoChangeFlow(String orderSn,Long acctId,BigDecimal beforBal,BigDecimal afterBal,BigDecimal changeAmount,Long sourceId){
        //插入传入的Member对应的邀流水表(直邀或间邀)
        AcctSettleInfo acctSettleInfo = new AcctSettleInfo();
        acctSettleInfo.setOrderNo(orderSn);
        acctSettleInfo.setAcctId(acctId);
        acctSettleInfo.setBeforBal(beforBal);
        acctSettleInfo.setAfterBal(afterBal);
        acctSettleInfo.setChange(changeAmount);
        acctSettleInfo.setFlowType(StaticConstant.FLOW_TYPE_INCOME);
        acctSettleInfo.setFlowTypeDetail(StaticConstant.FLOW_TYPE_DETAIL_INCOME_COMMISSION_INCOME);
        acctSettleInfo.setSourceId(sourceId);
        acctSettleInfo.setInsertTime(new Date());
        this.saveOrUpdate(acctSettleInfo);
    }
}
