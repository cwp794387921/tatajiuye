package com.tata.jiuye.controller;


import com.github.pagehelper.PageHelper;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.common.enums.FlowTypeEnum;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.*;
import com.tata.jiuye.model.*;
import com.tata.jiuye.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "WmsMemberController", description = "配送中心用户管理")
@RequestMapping("/wms")
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class WmsMemberController {

    @Resource
    private ReplenishableExamineMapper examineMapper;
    @Resource
    private UmsAdminService umsAdminService;
    @Resource
    private PmsProductMapper pmsProductMapper;
    @Resource
    private WmsMemberMapper memberMapper;
    @Resource
    private  WmsAreaMapper areaMapper;
    @Resource
    private OmsDistributionMapper distributionMapper;
    @Resource
    private PmsSkuStockMapper skuStockMapper;
    @Resource
    private AcctInfoMapper acctInfoMapper;
    @Resource
    private AcctSettleInfoMapper acctSettleInfoMapper;


    @ApiOperation("获取总仓出货单列表")
    @RequestMapping(value = "/shipmentList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult shipmentList(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<OmsDistribution> List= distributionMapper.queryCHList();
        return CommonResult.success(CommonPage.restPage(List));
    }

    @ApiOperation("出货操作")
    @RequestMapping(value = "/shipment", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult shipment(Long id) {
        OmsDistribution shipment=distributionMapper.selectByPrimaryKey(id.intValue());
        if(shipment==null){
            Asserts.fail("出货单不存在");
        }
        OmsDistribution distribution=new OmsDistribution();
        distribution.setShipmentId(id);
        distribution=distributionMapper.selectByParams(distribution);
        if(distribution==null){
            Asserts.fail("未找到对应补货单");
        }
        int number=distribution.getNumber();//补货数量
        //判断库存是否充足
        PmsSkuStock skuStock=new PmsSkuStock();
        skuStock.setProductId(distribution.getProductId());
        skuStock.setWmsMemberId(0L);
        skuStock=skuStockMapper.selectByParams(skuStock);
        if(skuStock==null){
            Asserts.fail("未找到库存");
        }
        if((skuStock.getStock()-number)<0){
            Asserts.fail("库存不足，无法出货");
        }
        //添加锁定库存
        skuStock.setLockStock(skuStock.getLockStock()+number);
        skuStockMapper.updateByPrimaryKey(skuStock);
        //更新补货单状态
        distribution.setStatus(1);//待收货
        //更新出货单状态
        shipment.setStatus(1);//待收货
        distributionMapper.updateByPrimaryKey(distribution);
        distributionMapper.updateByPrimaryKey(shipment);
        return CommonResult.success("操作成功");
    }


    @ApiOperation("获取配送用户列表")
    @RequestMapping(value = "/memberList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult memberList(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,@RequestParam Map<String,Object> params) {
        PageHelper.startPage(pageNum, pageSize);
        List<WmsMemberAreaDetail> memberList= memberMapper.queryMemberDetail(params);
        return CommonResult.success(CommonPage.restPage(memberList));
    }


    @ApiOperation("配送用户更改绑定上级")
    @RequestMapping(value = "/changeParent", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult changeParent(Long memberId,Long changeId) {
        if (memberId==0L){
            return CommonResult.failed("平台账号不允许修改上级");
        }
        WmsMember wmsMember=memberMapper.selectByPrimaryKey(memberId);
        if(wmsMember==null){
            return CommonResult.failed("用户信息不存在");
        }
        WmsMember changeInfo=memberMapper.selectByPrimaryKey(changeId);
        if (changeInfo==null){
            return CommonResult.failed("上级信息不存在");
        }
        wmsMember.setParentId(changeId);
        wmsMember.setUpdateTime(new Date());
        memberMapper.updateByPrimaryKey(wmsMember);
        return CommonResult.success("更改成功");
    }


    @ApiOperation("补货审核列表")
    @RequestMapping(value = "/replenishableList", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult replenishableList(@RequestParam Map<String,Object> params) {
        List<ReplenishableExamine> list=  examineMapper.queryList(params);
        return CommonResult.success(list);
    }


    @ApiOperation("补货审核接口")
    @RequestMapping(value = "/replenishable", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult replenishable(String umsAdminUserName,Long id,Long status) {
        UmsAdmin umsAdmin = umsAdminService.getAdminByUsername(umsAdminUserName);
        if(umsAdmin == null){
            return CommonResult.failed("当前用户未登录");
        }
        ReplenishableExamine examine=examineMapper.selectByPrimaryKey(id);
        if (examine==null){
            return CommonResult.failed("记录不存在");
        }
        WmsMember wmsMember=memberMapper.selectByPrimaryKey(examine.getApplyId());
        if(wmsMember==null){
            return CommonResult.failed("配送用户不存在");
        }
        WmsArea area=new WmsArea();
        area.setStatus(1);
        area.setWmsMemberId(wmsMember.getId());
         area= areaMapper.selectByParams(area);
        PmsProduct pmsProduct=pmsProductMapper.selectByPrimaryKey(examine.getProductId());
        if(pmsProduct==null){
            return CommonResult.failed("商品不存在");
        }
        if("1,2".contains(status.toString())){
            //1审核通过
            //2审核驳回
            log.info("补货申请id["+id+"]审核，状态["+status+"]");
            examine.setStatus(status.intValue());//待收货
            examine.setApplyId(umsAdmin.getId());
            examine.setApplyName(umsAdmin.getNickName());
            examine.setUpdateTime(new Date());
        }else{
            return CommonResult.failed("审核状态异常");
        }
        if(status==1){
            //收货审核通过
            //查找补货单
            OmsDistribution distribution=new OmsDistribution();
            distribution.setReplenishableId(examine.getId());
             distribution=distributionMapper.selectByParams(distribution);
             if (distribution==null){
                 return CommonResult.validateFailed("补货单不存在");
             }
             //查找出货单
            OmsDistribution Shipment=new OmsDistribution();
            Shipment.setId(distribution.getShipmentId().intValue());
            Shipment=distributionMapper.selectByParams(Shipment);
            if(Shipment==null){
                return CommonResult.validateFailed("出货单不存在");
            }
            //添加库存
            PmsSkuStock pmsSkuStock=new PmsSkuStock();
            pmsSkuStock.setWmsMemberId(wmsMember.getId());
            pmsSkuStock.setProductId(pmsProduct.getId());
            pmsSkuStock=skuStockMapper.selectByParams(pmsSkuStock);
            if(pmsSkuStock==null){
                pmsSkuStock=new PmsSkuStock();
                //查找总仓库存
                PmsSkuStock centerSkuStock=new PmsSkuStock();
                centerSkuStock.setWmsMemberId(0L);
                centerSkuStock.setProductId(pmsProduct.getId());
                centerSkuStock=skuStockMapper.selectByParams(centerSkuStock);
                //复制库存信息
                BeanUtils.copyProperties(centerSkuStock,pmsSkuStock);
                pmsSkuStock.setStock(examine.getNumber());
                pmsSkuStock.setLockStock(0);
                pmsSkuStock.setSale(0);
                pmsSkuStock.setLowStock(10);
                pmsSkuStock.setWmsMemberId(wmsMember.getId());
                skuStockMapper.insert(pmsSkuStock);
            }else {
                pmsSkuStock.setStock(pmsSkuStock.getStock()+examine.getNumber());
                skuStockMapper.updateByPrimaryKey(pmsSkuStock);
            }
            //扣减出货仓库存
            PmsSkuStock ShipmentSkuStock=new PmsSkuStock();
            ShipmentSkuStock.setWmsMemberId(Shipment.getWmsMemberId());
            ShipmentSkuStock.setProductId(Shipment.getProductId());
            ShipmentSkuStock=skuStockMapper.selectByParams(ShipmentSkuStock);
            if(ShipmentSkuStock==null){
                return CommonResult.validateFailed("出货库存不存在");
            }
            ShipmentSkuStock.setLockStock(ShipmentSkuStock.getLockStock()-examine.getNumber());
            ShipmentSkuStock.setStock(ShipmentSkuStock.getStock()-examine.getNumber());
            skuStockMapper.updateByPrimaryKey(ShipmentSkuStock);
            //查找账户信息
            AcctInfo BHacctInfo=acctInfoMapper.selectByWmsId(wmsMember.getId());//补货账户
            if(BHacctInfo==null){
                return CommonResult.validateFailed("补货账户不存在");
            }
            AcctInfo CHacctInfo=acctInfoMapper.selectByWmsId(Shipment.getWmsMemberId());//出货账户
            if(CHacctInfo==null){
                return CommonResult.validateFailed("出货账户不存在");
            }
            //添加补货收益流水
            AcctSettleInfo acctSettleInfo=new AcctSettleInfo();
            acctSettleInfo.setOrderNo(distribution.getOrderSn());
            acctSettleInfo.setAcctId(BHacctInfo.getId());
            acctSettleInfo.setBeforBal(BHacctInfo.getBalance());
            acctSettleInfo.setChangeAmount(distribution.getProfit());
            acctSettleInfo.setAfterBal(BHacctInfo.getBalance().add(distribution.getProfit()));
            acctSettleInfo.setInsertTime(new Date());
            acctSettleInfo.setFlowType(FlowTypeEnum.INCOME.value);//收入
            acctSettleInfo.setFlowTypeDetail(FlowTypeEnum.STORAGE_ALLOWANCE.value);//仓补
            acctSettleInfoMapper.insert(acctSettleInfo);
            BHacctInfo.setBalance(BHacctInfo.getBalance().add(distribution.getProfit()));
            BHacctInfo.setUpdateTime(new Date());
            acctInfoMapper.updateByPrimaryKey(BHacctInfo);
            //添加出货收益流水
            acctSettleInfo=new AcctSettleInfo();
            acctSettleInfo.setOrderNo(Shipment.getOrderSn());
            acctSettleInfo.setAcctId(CHacctInfo.getId());
            acctSettleInfo.setBeforBal(CHacctInfo.getBalance());
            acctSettleInfo.setChangeAmount(Shipment.getProfit());
            acctSettleInfo.setAfterBal(CHacctInfo.getBalance().add(Shipment.getProfit()));
            acctSettleInfo.setInsertTime(new Date());
            acctSettleInfo.setFlowType(FlowTypeEnum.INCOME.value);//收入
            acctSettleInfo.setFlowTypeDetail(FlowTypeEnum.DELIVERY_FEE.value);//仓补
            acctSettleInfoMapper.insert(acctSettleInfo);
            CHacctInfo.setBalance(CHacctInfo.getBalance().add(Shipment.getProfit()));
            CHacctInfo.setUpdateTime(new Date());
            acctInfoMapper.updateByPrimaryKey(CHacctInfo);
            //补货单状态完成
            distribution.setStatus(5);//更新补货单状态
            distributionMapper.updateByPrimaryKey(distribution);
            //收货单状态完成
            Shipment.setStatus(5);
            distributionMapper.updateByPrimaryKey(Shipment);
        }
        if(status==2){
            //收货确认驳回
            examine.setApplyId(umsAdmin.getId());
            examine.setApplyName(umsAdmin.getNickName());
            examine.setUpdateTime(new Date());
            //更改补货单状态
            OmsDistribution distribution=new OmsDistribution();
            distribution.setReplenishableId(examine.getId());
            distribution=distributionMapper.selectByParams(distribution);
            if (distribution==null){
                return CommonResult.validateFailed("补货单不存在");
            }
            distribution.setStatus(1);
            distributionMapper.updateByPrimaryKey(distribution);
        }
        examineMapper.updateByPrimaryKey(examine);
        return CommonResult.success("操作成功");
    }

}
