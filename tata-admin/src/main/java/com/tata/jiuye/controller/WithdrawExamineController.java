package com.tata.jiuye.controller;

import com.github.pagehelper.PageHelper;
import com.tata.jiuye.DTO.WithdrawExamineQueryParam;
import com.tata.jiuye.DTO.WithdrawExamineQueryResult;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.mapper.WithdrawalExamineMapper;
import com.tata.jiuye.model.UmsAdmin;
import com.tata.jiuye.service.UmsAdminService;
import com.tata.jiuye.utils.HttpTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultDefaultValueProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "WithdrawExamineController", value = "后台提现相关业务")
@RequestMapping("/adminWithdrawExamine")
@RequiredArgsConstructor
public class WithdrawExamineController {

    @Value("${requestempleurl}")
    private String REQUEST_TEMPLATE_URL;

    @Autowired
    private UmsAdminService umsAdminService;

    @Resource
    private WithdrawalExamineMapper withdrawalExamineMapper;

    @ApiOperation("通过查询条件获取所有提现申请列表(后台用)")
    @RequestMapping(value = "/allWithdrawApplicationPage", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult allWithdrawApplicationPage (@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,@RequestParam Map<String,Object> params){
        PageHelper.startPage(pageNum, pageSize);
        List<WithdrawExamineQueryResult> list = withdrawalExamineMapper.queryList(params);
        return CommonResult.success(CommonPage.restPage(list));
    }


    @ApiOperation("提现审批(后台用)")
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult approve(@RequestParam @ApiParam("当前登录管理员用户名")String umsAdminUserName,@RequestParam @ApiParam("提现申请表ID")Long withdrExamineId,
                                @RequestParam @ApiParam("操作编码:PASS->通过,REFUSE->拒绝") String operateType) {
        UmsAdmin umsAdmin = umsAdminService.getAdminByUsername(umsAdminUserName);
        if(umsAdmin == null){
            return CommonResult.failed("当前用户未登录");
        }
        //通过查询条件获取所有提现申请列表
        String url = "withdrawExamine/approve";
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("umsAdminMemberId", umsAdmin.getId());
        param.add("umsAdminNickName", umsAdmin.getNickName());
        param.add("withdrExamineId", withdrExamineId);
        param.add("operateType", operateType);
        CommonResult commonResult = HttpTools.sendPostRequest(REQUEST_TEMPLATE_URL+url,param);
        return commonResult;
    }
}
