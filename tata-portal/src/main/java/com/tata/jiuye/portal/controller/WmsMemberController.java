package com.tata.jiuye.portal.controller;


import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.service.OmsPortalOrderService;
import com.tata.jiuye.portal.service.WmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Api(tags = "WmsMemberController", description = "配送中心用户管理")
@RequestMapping("/wms")
@RequiredArgsConstructor
public class WmsMemberController {

    private static final Logger log = LoggerFactory.getLogger(WmsMemberController.class);

    @Resource
    private WmsMemberService wmsMemberService;
    @Resource
    private OmsPortalOrderService portalOrderService;

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult info() {
        JSONObject result= wmsMemberService.selectMerberInfo();
        log.debug(result.toString());
        return CommonResult.success(result);
    }

    @ApiOperation("查询配送单列表（type=1时为配送单,status=0待接单 1待配送 ; type=2时为补货单,status=0待收货 1待审核 ; type=3时为借货单，status=0待取货，1待出货）")
    @RequestMapping(value = "/queryDistribution", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult queryDistribution() {
        JSONObject jsonObject=portalOrderService.queryDistribution();
        return CommonResult.success(jsonObject);
    }

    @ApiOperation("获取配送用户列表")
    @RequestMapping(value = "/queryUserList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult queryUserList() {
        List<WmsMemberAreaDetail> result= wmsMemberService.queryAllUser();
        return CommonResult.success(result);
    }

    @ApiOperation("转配送接口")
    @RequestMapping(value = "/changeDistribution", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult changeDistribution(Long changeId,Long orderId) {
        wmsMemberService.changeDistribution(changeId,orderId);
        return CommonResult.success("操作成功");
    }

    @ApiOperation("接单接口")
    @RequestMapping(value = "/acceptOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult acceptOrder(Long orderId) {
        wmsMemberService.acceptOrder(orderId);
        return CommonResult.success("操作成功");
    }

    @ApiOperation("送达接口")
    @RequestMapping(value = "/arriveOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult arriveOrder(Long orderId) {
        wmsMemberService.arriveOrder(orderId);
        return CommonResult.success("操作成功");
    }

    @ApiOperation("获取可补货列表")
    @RequestMapping(value = "/replenishableList ", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult replenishableList() {
        List<PmsProduct> list= wmsMemberService.queryReplenishableList();
        return CommonResult.success(list);
    }

    @ApiOperation("补货接口")
    @RequestMapping(value = "/replenishable", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult replenishable(@RequestBody List<ProductParams>  params) {
        if(params==null){
            return CommonResult.validateFailed("参数缺失");
        }
        wmsMemberService.replenishable(params);
        return CommonResult.success("操作成功");
    }





}
