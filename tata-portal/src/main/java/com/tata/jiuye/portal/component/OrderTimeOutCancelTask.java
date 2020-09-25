package com.tata.jiuye.portal.component;

import com.tata.jiuye.common.enums.FlowTypeEnum;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.*;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.service.AcctSettleInfoService;
import com.tata.jiuye.portal.service.OmsOrderItemService;
import com.tata.jiuye.portal.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by macro on 2018/8/24.
 * 订单超时取消并解锁库存的定时器
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class OrderTimeOutCancelTask {
    private Logger LOGGER =LoggerFactory.getLogger(OrderTimeOutCancelTask.class);
    @Autowired
    private OmsPortalOrderService portalOrderService;
    @Resource
    private OmsOrderMapper orderMapper;
    @Resource
    private OmsDistributionMapper distributionMapper;
    @Resource
    private AcctSettleInfoService acctSettleInfoService;
    @Resource
    private WmsMemberMapper wmsMemberMapper;
    @Resource
    private UmsMemberMapper umsMemberMapper;
    @Resource
    private AcctInfoMapper acctInfoMapper;
    @Resource
    private AcctSettleInfoMapper acctSettleInfoMapper;

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每10分钟扫描一次，扫描设定超时时间之前下的订单，如果没支付则取消该订单
     */
    @Scheduled(cron = "0 0/10 * ? * ?")
    private void cancelTimeOutOrder(){
        Integer count = portalOrderService.cancelTimeOutOrder();
        LOGGER.info("取消订单，并根据sku编号释放锁定库存，取消订单数量：{}",count);
        Map<String,Object>params=new HashMap<>();
        params.put("receiveTime",new Date());
        params.put("status",2);
        List<OmsOrder> orderList=orderMapper.queryList(params);
        if(!orderList.isEmpty()){
            LOGGER.info("订单超过收货时间，自动收货，数量：{}",orderList.size());
            for (OmsOrder order:orderList){
                //更新订单状态
                order.setStatus(3);//完成
                order.setModifyTime(new Date());
                orderMapper.updateByPrimaryKey(order);
                OmsDistribution omsDistribution=new OmsDistribution();
                omsDistribution.setOrderSn(order.getOrderSn());
                omsDistribution=distributionMapper.queryDistributionDetail(omsDistribution);
                if(omsDistribution==null){
                    LOGGER.info("配送单不存在");
                    continue;
                }
                WmsMember wmsMember=wmsMemberMapper.selectByPrimaryKey(omsDistribution.getWmsMemberId());
                if(wmsMember==null){
                    LOGGER.info("配送中心不存在");
                    continue;
                }
                omsDistribution.setStatus(5);//已完成
                distributionMapper.updateByPrimaryKey(omsDistribution);//更新配送单
                //添加账户流水
                UmsMember umsMember=umsMemberMapper.selectByPrimaryKey(omsDistribution.getUmsMemberId());
                if(umsMember==null){
                    LOGGER.info("用户信息不存在");
                    continue;
                }
                AcctInfo acctInfo=acctInfoMapper.selectByWmsId(wmsMember.getId());
                if (acctInfo==null){
                    LOGGER.info("账户不存在");
                    continue;
                }
                AcctSettleInfo acctSettleInfo=new AcctSettleInfo();
                acctSettleInfo.setAcctId(acctInfo.getId());
                acctSettleInfo.setOmsDistributionNo(omsDistribution.getId());
                acctSettleInfo.setOrderNo(omsDistribution.getOrderSn());
                acctSettleInfo.setBeforBal(acctInfo.getBalance());
                acctSettleInfo.setChangeAmount(omsDistribution.getProfit());
                //添加账户收益
                acctInfo.setBalance(acctInfo.getBalance().add(omsDistribution.getProfit()));
                acctSettleInfo.setAfterBal(acctInfo.getBalance());
                acctSettleInfo.setInsertTime(new Date());
                acctSettleInfo.setFlowType(FlowTypeEnum.INCOME.value);
                acctSettleInfo.setFlowTypeDetail(FlowTypeEnum.DELIVERY_FEE.value);
                acctSettleInfo.setSourceId(omsDistribution.getUmsMemberId());
                acctSettleInfoMapper.insert(acctSettleInfo);//插入账户流水
                acctInfoMapper.updateByPrimaryKey(acctInfo);//更新账户信息
            }
        }
    }
}
