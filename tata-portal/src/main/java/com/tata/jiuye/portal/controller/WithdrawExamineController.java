package com.tata.jiuye.portal.controller;

import cn.hutool.core.util.StrUtil;
import com.tata.jiuye.DTO.WithDrawDetailAcctSettleInfoResult;
import com.tata.jiuye.DTO.WithdrawExamineQueryParam;
import com.tata.jiuye.DTO.WithdrawExamineQueryResult;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.portal.service.UmsMemberService;
import com.tata.jiuye.portal.service.WithdrawalExamineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@Api(tags = "WithdrawExamineController", description = "提现相关业务")
@RequestMapping("/withdrawExamine")
@RequiredArgsConstructor
@Slf4j
public class WithdrawExamineController {

    @Autowired
    private WithdrawalExamineService withdrawalExamineService;
    @Autowired
    private UmsMemberService memberService;

    @ApiOperation("提现申请(小程序端用)")
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult apply(@RequestParam @ApiParam("提现金额")BigDecimal withdrawAmount,
                              @RequestParam @ApiParam("提现申请账户类型:COMMISSION->分佣账户,DELIVERYCENTER->配送中心账户")String accountType,
                              @RequestParam @ApiParam("提现到账类型 1微信 2支付宝 3银行卡")String type,
                              @RequestParam @ApiParam("真实姓名")String name,
                              @RequestParam @ApiParam("提现账户(微信号，手机号或银行卡号)")String accountId,
                              @RequestParam(required = false) @ApiParam("所在行 类型3银行卡时必传")String bank) {
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            return CommonResult.failed("用户未登录");
        }
        if(withdrawAmount == null || BigDecimal.ZERO.equals(withdrawAmount)){
            return CommonResult.failed("提现金额不能为空或0");
        }
        if(type.equals("3")&& StrUtil.isEmpty(bank)){
            return CommonResult.failed("参数缺失");
        }
        withdrawalExamineService.insertWithdrawalExamine(currentMember,withdrawAmount,accountType,type,name,accountId,bank);
        return CommonResult.success("提现申请已提交");
    }


    @ApiOperation("我的提现申请列表(小程序端用)")
    @RequestMapping(value = "/myWithdrawApplicationPage", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult myWithdrawApplicationPage(@RequestParam @ApiParam("页码")Integer pageNum,@RequestParam @ApiParam("每页条数")Integer pageSize) {
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            return CommonResult.failed("用户未登录");
        }
        CommonPage<WithdrawExamineQueryResult> resultCommonPage = withdrawalExamineService.getMyWithdrawalExaminePage(pageNum,pageSize,currentMember.getId());
        return CommonResult.success(resultCommonPage);
    }


    @ApiOperation("通过查询条件获取所有提现申请列表(后台用)")
    @RequestMapping(value = "/allWithdrawApplicationPage", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult allWithdrawApplicationPage(@RequestBody WithdrawExamineQueryParam param) {
        log.info("----------------参数 param :"+param);
        CommonPage<WithdrawExamineQueryResult> resultCommonPage = withdrawalExamineService.getAllWithdrawalExaminePageByQueryParam(param);
        return CommonResult.success(resultCommonPage);
    }


    @ApiOperation("提现审批(后台用)")
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult approve(@RequestParam @ApiParam("提现审批人管理员用户ID")Long umsAdminMemberId,@RequestParam @ApiParam("提现审批人管理员用户昵称")String umsAdminNickName,
                                @RequestParam @ApiParam("提现申请表ID")Long withdrExamineId,@RequestParam @ApiParam("操作编码:PASS->通过,REFUSE->拒绝") String operateType) {
        withdrawalExamineService.passOrRejectWithdrawalExamine(umsAdminMemberId,umsAdminNickName,withdrExamineId,operateType);
        return CommonResult.success("提现审批成功");
    }

    @ApiOperation("通过流水ID获取提现流水详情")
    @RequestMapping(value = "/getWithDrawlDetalByacctSettleInfoId", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getWithDrawlDetalByacctSettleInfoId(@RequestParam @ApiParam("流水号ID") Long acctSettleInfoId){
        WithDrawDetailAcctSettleInfoResult result = withdrawalExamineService.getWithDrawlDetalByacctSettleInfoId(acctSettleInfoId);
        return CommonResult.success(result);
    }
}
