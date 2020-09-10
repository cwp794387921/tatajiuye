package com.tata.jiuye.controller;


import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.common.enums.FlowTypeEnum;
import com.tata.jiuye.common.utils.OrderUtil;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger2.mappers.LicenseMapper;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
        WmsMember wmsMember=memberMapper.selectByUmsId(examine.getApplyId());
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
        if("1,3,4".contains(status.toString())){
            //1补货审核通过 待确认收货
            //3收货审核通过
            //4补货申请驳回
            //5确认收货驳回
            log.info("补货申请id["+id+"]审核，状态["+status+"]");
            examine.setStatus(status.intValue());//待收货
            examine.setApplyId(umsAdmin.getId());
            examine.setApplyName(umsAdmin.getNickName());
            examine.setUpdateTime(new Date());
        }else{
            return CommonResult.failed("审核状态异常");
        }
        BigDecimal profit=new BigDecimal(0);
        switch (wmsMember.getLevel()){
            case 1:
                profit=pmsProduct.getDeliveryCenterWarehouseReplenishment();
                break;
            case 2:
                profit=pmsProduct.getRegionalWarehouseReplenishment();
                break;
            case 3:
                profit=pmsProduct.getWebmasterWarehouseReplenishment();
                break;
        }
        if(status==1){
            //待收货
            //新增补货单
            OmsDistribution distribution=new OmsDistribution();
            distribution.setOrderSn(OrderUtil.getBhOrderNo());
            distribution.setStatus(0);//待收货
            distribution.setGoodsTitle(pmsProduct.getName());
            distribution.setGoodsSubtitle(pmsProduct.getSubTitle());
            distribution.setGoodsImg(pmsProduct.getPic());
            distribution.setPrice(pmsProduct.getDeliveryCenterPrice());
            distribution.setNumber(examine.getNumber());
            distribution.setSubPrice(pmsProduct.getDeliveryCenterPrice().
                    multiply(new BigDecimal(examine.getNumber())));
            distribution.setName(wmsMember.getNickname());
            distribution.setHeadImg(wmsMember.getIcon());
            distribution.setAddress(area.getAddress());
            distribution.setCreateTime(new Date());
            distribution.setWmsMemberId(wmsMember.getId());
            distribution.setType(2);//补货单
            distribution.setProfit(profit);
            distribution.setPhone(wmsMember.getPhone());
            distribution.setProductId(pmsProduct.getId());
            distribution.setReplenishableId(examine.getId());
            distributionMapper.insert(distribution);
        }
        if(status==3){
            //收货审核通过
            //查找补货单
            OmsDistribution distribution=new OmsDistribution();
            distribution.setReplenishableId(examine.getId());
             distribution=distributionMapper.selectByParams(distribution);
             if (distribution==null){
                 return CommonResult.validateFailed("补货单不存在");
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
            //查找账户信息
            AcctInfo acctInfo=acctInfoMapper.selectByWmsId(wmsMember.getId());
            if(acctInfo==null){
                return CommonResult.validateFailed("配送账户不存在");
            }
            //添加收益
            AcctSettleInfo acctSettleInfo=new AcctSettleInfo();
            acctSettleInfo.setOrderNo(distribution.getOrderSn());
            acctSettleInfo.setAcctId(acctInfo.getId());
            acctSettleInfo.setBeforBal(acctInfo.getBalance());
            acctSettleInfo.setChangeAmount(distribution.getProfit());
            acctSettleInfo.setAfterBal(acctInfo.getBalance().add(distribution.getProfit()));
            acctSettleInfo.setInsertTime(new Date());
            acctSettleInfo.setFlowType(FlowTypeEnum.INCOME.value);
            acctSettleInfo.setFlowTypeDetail(FlowTypeEnum.STORAGE_ALLOWANCE.value);
            acctSettleInfoMapper.insert(acctSettleInfo);
            //补货单状态完成
            distribution.setStatus(5);//更新补货单状态
            distributionMapper.updateByPrimaryKey(distribution);
        }
        if(status==5){
            //收货确认驳回
            examine.setApplyId(umsAdmin.getId());
            examine.setApplyName(umsAdmin.getNickName());
            examine.setUpdateTime(new Date());
            examine.setImgs(null);
            //更改补货单状态
            OmsDistribution distribution=new OmsDistribution();
            distribution.setReplenishableId(examine.getId());
            distribution=distributionMapper.selectByParams(distribution);
            if (distribution==null){
                return CommonResult.validateFailed("补货单不存在");
            }
            distribution.setStatus(0);
            distributionMapper.updateByPrimaryKey(distribution);
        }
        examineMapper.updateByPrimaryKey(examine);
        return CommonResult.success("操作成功");
    }

}
