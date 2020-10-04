package com.tata.jiuye.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.AcctInfoMapper;
import com.tata.jiuye.mapper.WmsMemberMapper;
import com.tata.jiuye.model.AcctInfo;
import com.tata.jiuye.model.AcctSettleInfo;
import com.tata.jiuye.model.WmsMember;
import com.tata.jiuye.portal.common.constant.StaticConstant;
import com.tata.jiuye.portal.service.AcctInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 账户表相关业务实现1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AcctInfoServiceImpl extends ServiceImpl<AcctInfoMapper, AcctInfo> implements AcctInfoService {
    @Resource
    private AcctInfoMapper acctInfoMapper;
    @Resource
    private WmsMemberMapper wmsMemberMapper;

    @Override
    public void saveOrUpdateAcctInfo(AcctInfo acctInfo){
        this.saveOrUpdate(acctInfo);
    }

    @Override
    public AcctInfo getAcctInfoByMemberId(Long memberId,String acctType){
        log.info("--------------------通过用户ID获取账户信息  开始--------------------");
        Map<String,Object> params=new HashMap<>();
        if(acctType.equals("COMMISSION")){
            params.put("memberId",memberId);
        }else if(acctType.equals("DELIVERYCENTER")){
            Map<String,Object>queryParams=new HashMap<>();
            queryParams.put("umsMemberId",memberId);
            WmsMember wmsMember=wmsMemberMapper.selectByUmsId(queryParams);
            if(wmsMember==null){
                Asserts.fail("配送用户信息不存在");
            }
            params.put("branchId",wmsMember.getId());
        }
        log.info("--------------------通过用户ID获取账户信息  结束--------------------");
       return acctInfoMapper.getByMemberIdAndAcctType(params);
    }


    @Override
    public AcctSettleInfo updateAcctInfoByAmount(Long acctMemberId, BigDecimal changeAmount,String type,String acctType){
        log.info("--------------------账户金额变动更新  开始--------------------");
        AcctSettleInfo acctSettleInfo = new AcctSettleInfo();
        log.info("--------------------变更账号的用户ID为 "+acctMemberId);
        log.info("--------------------变更金额为 "+changeAmount);
        //获取直邀/间邀账户 并增加余额
        AcctInfo acctInfo = this.getAcctInfoByMemberId(acctMemberId,acctType);
        if(acctInfo == null){
            Asserts.fail("获取不到对应的账户信息");
        }
        log.info("--------------------对应的变更账户信息为 "+acctInfo);
        BigDecimal balance = acctInfo.getBalance();
        BigDecimal lockAmount = acctInfo.getLockAmount();
        acctSettleInfo.setBeforBal(balance);
        log.info("--------------------变更前余额为 "+balance);
        if(StaticConstant.FLOW_TYPE_INCOME.equals(type)){
            log.info("--------------------变更类型为 收入--------------------");
            balance = balance.add(changeAmount);
        }
        else if(StaticConstant.FLOW_TYPE_EXPENDITURE.equals(type)){
            log.info("--------------------变更类型为 支出--------------------");
            balance = balance.subtract(changeAmount);
            if(balance.compareTo(BigDecimal.ZERO) < 0){
                Asserts.fail("支出金额超过余额,支出失败");
            }
            lockAmount = lockAmount.subtract(changeAmount);
            acctInfo.setLockAmount(lockAmount);
        }
        acctInfo.setBalance(balance);
        log.info("--------------------变更后余额为 "+balance);
        acctSettleInfo.setAfterBal(balance);
        acctInfo.setUpdateTime(new Date());
        this.saveOrUpdateAcctInfo(acctInfo);
        acctSettleInfo.setAcctId(acctInfo.getId());
        log.info("--------------------账户金额变动更新  结束--------------------");
        return acctSettleInfo;
    }
}
