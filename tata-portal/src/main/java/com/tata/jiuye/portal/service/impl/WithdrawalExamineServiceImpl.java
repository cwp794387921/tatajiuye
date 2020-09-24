package com.tata.jiuye.portal.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.tata.jiuye.DTO.WithDrawDetailAcctSettleInfoResult;
import com.tata.jiuye.DTO.WithdrawExamineQueryParam;
import com.tata.jiuye.DTO.WithdrawExamineQueryResult;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.AcctInfoMapper;
import com.tata.jiuye.mapper.AcctSettleInfoMapper;
import com.tata.jiuye.mapper.WithdrawalExamineMapper;
import com.tata.jiuye.model.AcctInfo;
import com.tata.jiuye.model.AcctSettleInfo;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.model.WithdrawalExamine;
import com.tata.jiuye.portal.common.constant.StaticConstant;
import com.tata.jiuye.portal.common.constant.WithdrawStatusEnum;
import com.tata.jiuye.portal.service.AcctInfoService;
import com.tata.jiuye.portal.service.AcctSettleInfoService;
import com.tata.jiuye.portal.service.WithdrawalExamineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 提现申请相关业务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WithdrawalExamineServiceImpl extends ServiceImpl<WithdrawalExamineMapper, WithdrawalExamine> implements WithdrawalExamineService {

    @Resource
    private WithdrawalExamineMapper withdrawalExamineMapper;
    @Autowired
    private AcctInfoService acctInfoService;
    @Autowired
    private AcctSettleInfoService acctSettleInfoService;
    @Resource
    private AcctInfoMapper acctInfoMapper;
    @Resource
    private AcctSettleInfoMapper acctSettleInfoMapper;
    @Override
    public void insertWithdrawalExamine(UmsMember umsMember, BigDecimal withdrawAmount,String accountType,String type,String name,String accountId,String bank){
        log.info("----------------------插入提现申请 开始----------------------");
        log.info("----------------------提现申请参数 申请人信息为: "+umsMember);
        log.info("----------------------提现申请参数 提现金额: "+withdrawAmount);
        if(StringUtils.isEmpty(accountType)){
            Asserts.fail("提现账户类型不能为空");
        }
        //获取用户余额(指定账户类型)
        AcctInfo acctInfo = acctInfoService.getAcctInfoByMemberId(umsMember.getId(),accountType);
        BigDecimal lockAmount = acctInfo.getLockAmount();
        if(lockAmount == null){
            lockAmount = BigDecimal.ZERO;
        }
        BigDecimal balance = acctInfo.getBalance();
        BigDecimal actBalance = balance.subtract(lockAmount);
        if(actBalance.compareTo(withdrawAmount) < 0){
            Asserts.fail("提现金额不可大于余额");
        }
        WithdrawalExamine withdrawalExamine = new WithdrawalExamine();
        //申请人ID
        withdrawalExamine.setApplicantMemberId(umsMember.getId());
        withdrawalExamine.setApplicantMemberName(umsMember.getNickname());
        withdrawalExamine.setCreateTime(new Date());
        withdrawalExamine.setWithdrawalAmount(withdrawAmount);
        withdrawalExamine.setStatus(WithdrawStatusEnum.PENDING.getValue());
        withdrawalExamine.setAcctType(accountType);
        withdrawalExamine.setType(type);
        withdrawalExamine.setName(name);
        withdrawalExamine.setAccountId(accountId);
        withdrawalExamine.setBank(bank);
        withdrawalExamineMapper.insert(withdrawalExamine);
        //更新账户表锁定金额
        lockAmount = lockAmount.add(withdrawAmount);
        acctInfo.setLockAmount(lockAmount);
        acctInfoMapper.updateByPrimaryKey(acctInfo);
        log.info("----------------------插入提现申请 结束----------------------");
    }

    @Override
    public void passOrRejectWithdrawalExamine(Long umsAdminMemberId,String umsAdminNickName,Long withdrExamineId,String operateType){
        log.info("----------------------通过/拒绝提现申请 开始----------------------");
        if(withdrExamineId == null){
            Asserts.fail("请选择提现申请记录");
        }
        log.info("----------------------参数审批人为 : "+umsAdminNickName);
        log.info("----------------------参数提现审批申请表ID为 : "+withdrExamineId);
        log.info("----------------------参数 操作类型为 : "+operateType);
        WithdrawalExamine withdrawalExamine = this.getById(withdrExamineId);
        if(StringUtils.isEmpty(operateType)){
            Asserts.fail("操作类型为空");
        }
        if(withdrawalExamine == null){
            Asserts.fail("找不到对应的提现申请记录");
        }
        //如果操作类型为通过,则改该状态为1
        if(StaticConstant.APPROVAL_OPERATION_PASS.equals(operateType)){
            withdrawalExamine.setStatus(WithdrawStatusEnum.PASS.getValue());
        }
        else if(StaticConstant.APPROVAL_OPERATION_REFUSE.equals(operateType)){
            withdrawalExamine.setStatus(WithdrawStatusEnum.REJECT.getValue());
        }
        withdrawalExamine.setApproverMemberId(umsAdminMemberId);
        withdrawalExamine.setApproverMemberName(umsAdminNickName);
        withdrawalExamine.setUpdateTime(new Date());
        //如果操作类型为通过,插入提现流水
        if(StaticConstant.APPROVAL_OPERATION_PASS.equals(operateType)){
           AcctSettleInfo acctSettleInfo = acctSettleInfoService.insertWithdrawExamineAcctSettleInfo(withdrawalExamine.getApplicantMemberId(),withdrawalExamine.getWithdrawalAmount(),withdrawalExamine.getAcctType());
           withdrawalExamine.setAcctSettleInfoId(acctSettleInfo.getId());
        }
        this.saveOrUpdate(withdrawalExamine);
        log.info("----------------------执行结果为 : "+withdrawalExamine);
        log.info("----------------------通过/拒绝提现申请 结束----------------------");
    }

    @Override
    public CommonPage<WithdrawExamineQueryResult> getMyWithdrawalExaminePage(Integer pageNum,Integer pageSize,Long memberId){
        log.info("----------------------获取我的提现申请 开始----------------------");
        if(memberId == null){
            Asserts.fail("用户未登录");
        }
        log.info("----------------------参数 页码为 : "+pageNum);
        log.info("----------------------参数 每页条数为 : "+pageSize);
        log.info("----------------------参数 申请人ID为 : "+memberId);
        PageHelper.startPage(pageNum,pageSize);
        List<WithdrawExamineQueryResult> withdrawalExamines = withdrawalExamineMapper.getMyWithdrawalExamineList(memberId);
        log.info("----------------------查询结果 withdrawalExamines : "+withdrawalExamines);
        CommonPage<WithdrawExamineQueryResult> resultPage = CommonPage.restPage(withdrawalExamines);
        log.info("----------------------获取我的提现申请 结束----------------------");
        return resultPage;
    }


    @Override
    public CommonPage<WithdrawExamineQueryResult> getAllWithdrawalExaminePageByQueryParam(WithdrawExamineQueryParam withdrawExamineQueryParam){
        log.info("----------------------根据查询条件获取提现申请 开始----------------------");
        if(withdrawExamineQueryParam == null){
            Asserts.fail("查询参数为空");
        }
        if(withdrawExamineQueryParam.getPageNum() == null){
            withdrawExamineQueryParam.setPageNum(1);
        }
        if(withdrawExamineQueryParam.getPageSize() == null){
            withdrawExamineQueryParam.setPageSize(10);
        }
        log.info("----------------------参数 withdrawExamineQueryParam : "+withdrawExamineQueryParam);
        PageHelper.startPage(withdrawExamineQueryParam.getPageNum(),withdrawExamineQueryParam.getPageSize());
        List<WithdrawExamineQueryResult> results = withdrawalExamineMapper.getAllWithdrawalExamineListByQueryParam(withdrawExamineQueryParam);
        log.info("----------------------查询结果 results : "+results);
        CommonPage<WithdrawExamineQueryResult> resultPage = CommonPage.restPage(results);
        log.info("----------------------根据查询条件获取提现申请 开始----------------------");
        return resultPage;
    }


    @Override
    public WithDrawDetailAcctSettleInfoResult getWithDrawlDetalByacctSettleInfoId(Long acctSettleInfoId){
        if(acctSettleInfoId == null){
            Asserts.fail("流水ID不能为空");
        }
        return acctSettleInfoMapper.getWithDrawlDetalByacctSettleInfoId(acctSettleInfoId);
    }
}
