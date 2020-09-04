package com.tata.jiuye.portal.controller;

import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.model.AcctInfo;
import com.tata.jiuye.model.AcctSettleInfo;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.portal.service.AcctInfoService;
import com.tata.jiuye.portal.service.AcctSettleInfoService;
import com.tata.jiuye.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@Api(tags = "BalanceFlowController", description = "余额及余额明细管理")
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceFlowController {

    @Resource
    private UmsMemberService memberService;
    @Resource
    private AcctSettleInfoService acctSettleInfoService;
    @Resource
    private AcctInfoService acctInfoService;


    @ApiOperation("获取当前用户总收入及今日收入总额")
    @RequestMapping(value = "/getTotalBalanceAndTodayIncome", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getTotalBalanceAndTodayIncome() {
        Map<String, BigDecimal> resultMap = new HashMap<>();
        UmsMember umsMember = memberService.getCurrentMember();
        if(umsMember == null){
            return CommonResult.failed("当前用户未登录");
        }
        BigDecimal todayIncome = acctSettleInfoService.getTodayIncome(umsMember.getId());
        BigDecimal totalIncome = acctSettleInfoService.getTotalIncome(umsMember.getId());
        resultMap.put("todayIncome",todayIncome);
        resultMap.put("totalIncome",totalIncome);
        return CommonResult.success(resultMap);
    }

    @ApiOperation("获取当前用户余额")
    @RequestMapping(value = "/getBalance", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getBalance(){
        UmsMember umsMember = memberService.getCurrentMember();
        if(umsMember == null){
            return CommonResult.failed("当前用户未登录");
        }
        AcctInfo acctInfo = acctInfoService.getAcctInfoByMemberId(umsMember.getId());
        BigDecimal balance = BigDecimal.ZERO;
        if(acctInfo != null){
            balance = acctInfo.getBalance();
        }
        return CommonResult.success(balance);
    }

    @ApiOperation("获取当前用户余额明细(当flowType传空时,查余额明细,当flowType传'income'时,查收入明细,flowType传'expenditure'时查支出明细)")
    @RequestMapping(value = "/getBalanceFlow", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getBalanceFlow(@RequestParam(defaultValue = "1") @ApiParam("页码")Integer pageNum, @RequestParam(defaultValue = "10") @ApiParam("每页条数") Integer pageSize,
                                       @RequestParam @ApiParam("年份")String year, @RequestParam @ApiParam("月份")String month, @RequestParam(required = false) @ApiParam("查询流水类型:空->查余额明细,income->收入明细,expenditure查支出明细")String flowType) {
        UmsMember umsMember = memberService.getCurrentMember();
        if(umsMember == null){
            return CommonResult.failed("当前用户未登录");
        }
        CommonPage<AcctSettleInfo> resultPage = acctSettleInfoService.getBalanceAndFlow(pageNum,pageSize,umsMember.getId(),year,month,flowType);
        return CommonResult.success(resultPage);
    }
}
