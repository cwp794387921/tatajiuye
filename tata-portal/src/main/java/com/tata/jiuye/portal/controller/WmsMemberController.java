package com.tata.jiuye.portal.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.common.service.RedisService;
import com.tata.jiuye.mapper.OmsDistributionMapper;
import com.tata.jiuye.mapper.WmsMemberMapper;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.service.OmsPortalOrderService;
import com.tata.jiuye.portal.service.WmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @Resource
    private WmsMemberMapper wmsMemberMapper;
    @Resource
    private OmsDistributionMapper distributionMapper;


    @Resource
    private RedisService redisService;


    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult info() {
        JSONObject result= wmsMemberService.selectMerberInfo();
        return CommonResult.success(result);
    }

    @ApiOperation("查询配送单列表（type=1时为配送单,status=0待接单 1待配送 ; type=2时为补货单,status=0待补货 1待收货 2待审核; type=3时为出货单，status=0待出货，1待收货）")
    @RequestMapping(value = "/queryDistribution", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult queryDistribution() {
        JSONObject jsonObject=portalOrderService.queryDistribution();
        return CommonResult.success(jsonObject);
    }

    @ApiOperation("获取配送用户列表")
    @RequestMapping(value = "/queryUserList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult queryUserList(@RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,String params) {
        List<WmsMemberAreaDetail> result= wmsMemberService.queryAllUser(pageSize,pageNum,params);
        return CommonResult.success(result);
    }

    @ApiOperation("获取可选地区列表")
    @RequestMapping(value = "/queryAreaList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult queryAreaList() {
        List<WmsArea> result= wmsMemberService.queryAllArea();
        return CommonResult.success(result);
    }

    @ApiOperation("转配送接口")
    @RequestMapping(value = "/changeDistribution", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult changeDistribution(Long changeId,Long orderId) {
        wmsMemberService.changeDistribution(changeId,orderId);
        return CommonResult.success("操作成功");
    }

    @ApiOperation("配送单接单接口")
    @RequestMapping(value = "/acceptOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult acceptOrder(Long orderId) {
        wmsMemberService.acceptOrder(orderId);
        return CommonResult.success("操作成功");
    }

    @ApiOperation("额度转让接口")
    @RequestMapping(value = "/creditLineChange", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult creditLineChange(Long memberId,String value) {
        wmsMemberService.creditLineChange(memberId,value);
        return CommonResult.success("操作成功");
    }


    @ApiOperation("配送单取消接单接口")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult cancelOrder(Long orderId) {
        wmsMemberService.cancelOrder(orderId);
        return CommonResult.success("操作成功");
    }

    @ApiOperation("确认收货接口")
    @RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult confirmOrder(Long orderId) {
        wmsMemberService.confirmOrder(orderId);
        return CommonResult.success("操作成功");
    }



    @ApiOperation("配送单送达接口(废弃)")
    @RequestMapping(value = "/arriveOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult arriveOrder(Long orderId) {
        wmsMemberService.arriveOrder(orderId);
        return CommonResult.success("操作成功");
    }

    @ApiOperation("取消补货申请,或拒绝出货申请  type=1 补货单取消申请 type=2 出货单拒绝申请")
    @RequestMapping(value = "/cancelReplenishable", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult cancelReplenishable(Long id,String type) {
        wmsMemberService.cancelReplenishable(id,type);
        return CommonResult.success("操作成功");
    }

    @ApiOperation("获取可补货列表")
    @RequestMapping(value = "/replenishableList", method = RequestMethod.POST)
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


    @ApiOperation("出货单出货接口")
    @RequestMapping(value = "/CHshipment", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult CHshipment(Long id) {
        wmsMemberService.CHshipment(id);
        return CommonResult.success("操作成功");
    }


    @ApiOperation("库存出货接口")
    @RequestMapping(value = "/shipment", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult shipment(Long id,Integer number) {
        if(id==null||number==null){
            return CommonResult.validateFailed("参数缺失");
        }
        wmsMemberService.shipment(id,number);
        return CommonResult.success("操作成功");
    }


    @ApiOperation("收货审核接口")
    @RequestMapping(value = "/replenishableCheck", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult replenishableCheck(@RequestBody ReplenishableParams params) {
        if(params==null){
            return CommonResult.validateFailed("参数缺失");
        }
        wmsMemberService.replenishableCheck(params.getId(),params.getImgs());
        return CommonResult.success("操作成功");
    }




}
