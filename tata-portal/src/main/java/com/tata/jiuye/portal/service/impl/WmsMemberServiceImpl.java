package com.tata.jiuye.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.common.utils.OrderUtil;
import com.tata.jiuye.mapper.*;
import com.tata.jiuye.model.*;
import com.tata.jiuye.common.enums.FlowTypeEnum;
import com.tata.jiuye.portal.service.UmsMemberInviteRelationService;
import com.tata.jiuye.portal.service.UmsMemberService;
import com.tata.jiuye.portal.service.WmsMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class WmsMemberServiceImpl implements WmsMemberService {

    @Resource
    private UmsMemberService memberService;

    @Resource
    private WmsMemberMapper wmsMemberMapper;
    @Resource
    private AcctInfoMapper acctInfoMapper;
    @Resource
    private AcctSettleInfoMapper acctSettleInfoMapper;
    @Resource
    private OmsDistributionMapper distributionMapper;
    @Resource
    private PmsSkuStockMapper pmsSkuStockMapper;
    @Resource
    private ChangeDistributionMapper changeDistributionMapper;
    @Resource
    private PmsProductMapper pmsProductMapper;
    @Resource
    private ReplenishableExamineMapper examineMapper;
    @Resource
    private WmsAreaMapper areaMapper;
    @Resource
    private UmsMemberInviteRelationService umsMemberInviteRelationService;
    @Resource
    private UmsMemberInviteRelationMapper umsMemberInviteRelationMapper;

    @Override
    public JSONObject selectMerberInfo(){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        WmsMember wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        AcctInfo acctInfo=acctInfoMapper.selectByWmsId(wmsMember.getId());
        if (acctInfo==null){
            Asserts.fail("账户信息不存在");
        }
        JSONObject result=new JSONObject();
        result.put("bal",acctInfo.getBalance());
        result.put("creditLine",wmsMember.getCreditLine());
        //查找库存列表
        List<PmsSkuStockDetail> stockList=pmsSkuStockMapper.queryStockByMemberId(wmsMember.getId());
        result.put("stock",stockList);
        //查找补货单
        OmsDistribution distribution=new OmsDistribution();
        distribution.setType(2);
        distribution.setWmsMemberId(wmsMember.getId());
        List<OmsDistribution> BhList=distributionMapper.queryList(distribution);
        result.put("bh",BhList);
        //查找借货单
        distribution.setType(3);
        List<OmsDistribution> JhList=distributionMapper.queryList(distribution);
        result.put("jh",JhList);
        return result;
    }

    @Override
    public List<WmsMemberAreaDetail> queryAllUser(){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        WmsMember wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        List<WmsMemberAreaDetail> memberAreaDetails=wmsMemberMapper.queryAllWmsUser(wmsMember.getId());
        return memberAreaDetails;
    }

    @Override
    public List<WmsArea> queryAllArea(){
        WmsArea area=new WmsArea();
        List<WmsArea> areas=  areaMapper.queryList(area);
        return areas;
    }

    @Override
    public void changeDistribution(Long changeId,Long orderId){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        WmsMember wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        WmsMember changeWmsMember=wmsMemberMapper.selectByPrimaryKey(changeId);
        if(changeWmsMember==null){
            Asserts.fail("所选配送中心不存在");
        }
        OmsDistribution omsDistribution=distributionMapper.selectByPrimaryKey(orderId.intValue());
        if(omsDistribution==null){
            Asserts.fail("配送单不存在");
        }
        //插入转配送记录表
        ChangeDistribution changeDistribution=new ChangeDistribution();
        changeDistribution.setCreateTime(new Date());
        changeDistribution.setChangeMemberId(changeWmsMember.getId());
        changeDistribution.setOrderSn(omsDistribution.getOrderSn());
        changeDistribution.setWmsMemberId(wmsMember.getId());
        changeDistributionMapper.insert(changeDistribution);
        //更改配送单配送人id
        omsDistribution.setWmsMemberId(changeId);
        distributionMapper.updateByPrimaryKey(omsDistribution);
    }

    @Override
    public void  acceptOrder(Long orderId){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        WmsMember wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        OmsDistribution omsDistribution=distributionMapper.selectByPrimaryKey(orderId.intValue());
        if(omsDistribution==null){
            Asserts.fail("配送单不存在");
        }
        omsDistribution.setStatus(1);//待配送
        distributionMapper.updateByPrimaryKey(omsDistribution);
        PmsSkuStock pmsSkuStock=new PmsSkuStock();
        pmsSkuStock.setProductId(omsDistribution.getProductId());
        pmsSkuStock.setWmsMemberId(wmsMember.getId());
         pmsSkuStock=pmsSkuStockMapper.selectByParams(pmsSkuStock);
        if(pmsSkuStock==null){
            Asserts.fail("库存不存在");
        }
        pmsSkuStock.setLockStock(pmsSkuStock.getLockStock()+omsDistribution.getNumber());//添加锁定库存
        //更新库存信息
        pmsSkuStockMapper.updateByPrimaryKeySelective(pmsSkuStock);
    }

    @Override
    public void arriveOrder(Long orderId){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        WmsMember wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        OmsDistribution omsDistribution=distributionMapper.selectByPrimaryKey(orderId.intValue());
        if(omsDistribution==null){
            Asserts.fail("配送单不存在");
        }
        omsDistribution.setStatus(5);//已完成
        distributionMapper.updateByPrimaryKey(omsDistribution);//更新配送单
        //清除锁定库存
        PmsSkuStock pmsSkuStock=new PmsSkuStock();
        pmsSkuStock.setProductId(omsDistribution.getProductId());
        pmsSkuStock.setWmsMemberId(wmsMember.getId());
        pmsSkuStock=pmsSkuStockMapper.selectByParams(pmsSkuStock);
        if(pmsSkuStock==null){
            Asserts.fail("库存不存在");
        }
        pmsSkuStock.setLockStock(pmsSkuStock.getLockStock()-omsDistribution.getNumber());//减少锁定库存
        pmsSkuStock.setStock(pmsSkuStock.getStock()-omsDistribution.getNumber());//减少实际库存
        pmsSkuStockMapper.updateByPrimaryKey(pmsSkuStock);//更新库存
        //添加账户流水
        AcctInfo acctInfo=acctInfoMapper.selectByWmsId(wmsMember.getId());
        if (acctInfo==null){
            Asserts.fail("账户不存在");
        }
        AcctSettleInfo acctSettleInfo=new AcctSettleInfo();
        acctSettleInfo.setAcctId(acctInfo.getId());
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


    @Override
    public  List<PmsProduct>  queryReplenishableList(){
        PmsProduct pmsProduct=new PmsProduct();
        pmsProduct.setIfUpgradeDistributionCenterProduct(0);
        pmsProduct.setIfJoinVipProduct(0);
        pmsProduct.setPublishStatus(1);
        List<PmsProduct>list=pmsProductMapper.queryList(pmsProduct);
        return list;
    }

    @Override
    public void  replenishableCheck(Long id,List<String> imgs){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        WmsMember wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        OmsDistribution distribution=distributionMapper.selectByPrimaryKey(id.intValue());
        if(distribution==null){
            Asserts.fail("补货单记录不存在");
        }
        if(distribution.getStatus()!=1){
            //状态1待收货才能提交审核
            Asserts.fail("补货单状态不正确");
        }
        //插入补货单收货审核表
        ReplenishableExamine examine=new ReplenishableExamine();
        examine.setApplyId(wmsMember.getId());
        examine.setApplyName(wmsMember.getNickname());
        examine.setCreateTime(new Date());
        examine.setProductId(distribution.getProductId());
        examine.setNumber(distribution.getNumber());
        String imgStr="";
        for (String img :imgs){
            if(StrUtil.isEmpty(imgStr)){
                imgStr+=img;
            }else {
                imgStr+=","+img;
            }
        }
        examine.setImgs(imgStr);
        examine.setStatus(0);
        Integer examineId= examineMapper.insert(examine);
        //修改补货单状态待审核
        distribution.setStatus(2);//待审核
        distribution.setReplenishableId(examine.getId());
        distributionMapper.updateByPrimaryKey(distribution);
    }

    @Override
    public void replenishable(List<ProductParams>  params){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        WmsMemberAreaDetail wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        Long parentId=wmsMember.getParentId();
        WmsMember parent=wmsMemberMapper.selectByPrimaryKey(parentId);
        if(parent==null){
            Asserts.fail("上级配送中心不存在");
        }
        BigDecimal subPrice=new BigDecimal(0);
        for (ProductParams param: params){
            Long id=param.getId();//商品id
            int num=param.getNumber();
            BigDecimal price=param.getPrice();
            subPrice=subPrice.add(new BigDecimal(num).multiply(price));
            if(subPrice.compareTo(wmsMember.getCreditLine())==1){
                Asserts.fail("补货总价超过授信额度");
            }
            //生成补货单
            PmsProduct pmsProduct=pmsProductMapper.selectByPrimaryKey(id);
            if(pmsProduct==null){
                Asserts.fail("商品不存在");
            }
            BigDecimal profit=new BigDecimal(0);//仓补收益
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
            OmsDistribution distribution=new OmsDistribution();
            distribution.setOrderSn(OrderUtil.getBhOrderNo());
            distribution.setStatus(0);//待收货
            distribution.setGoodsTitle(pmsProduct.getName());
            distribution.setGoodsSubtitle(pmsProduct.getSubTitle());
            distribution.setGoodsImg(pmsProduct.getPic());
            distribution.setPrice(price);
            distribution.setNumber(num);
            distribution.setSubPrice(price.multiply(new BigDecimal(num)));
            distribution.setName(wmsMember.getNickname());
            distribution.setHeadImg(wmsMember.getIcon());
            distribution.setAddress(wmsMember.getAddress());
            distribution.setCreateTime(new Date());
            distribution.setWmsMemberId(wmsMember.getId());
            distribution.setType(2);//补货单
            distribution.setProfit(profit.multiply(new BigDecimal(num)));
            distribution.setPhone(wmsMember.getPhone());
            distribution.setProductId(pmsProduct.getId());
            //上级生成出货单
            OmsDistribution Shipment=new OmsDistribution();
            Shipment.setOrderSn(OrderUtil.getChOrderNo());
            Shipment.setStatus(0);//待出货
            Shipment.setGoodsTitle(pmsProduct.getName());
            Shipment.setGoodsSubtitle(pmsProduct.getSubTitle());
            Shipment.setGoodsImg(pmsProduct.getPic());
            Shipment.setPrice(price);
            Shipment.setNumber(num);
            Shipment.setSubPrice(price.multiply(new BigDecimal(num)));
            Shipment.setName(wmsMember.getNickname());
            Shipment.setHeadImg(wmsMember.getIcon());
            Shipment.setAddress(wmsMember.getAddress());
            Shipment.setCreateTime(new Date());
            Shipment.setWmsMemberId(parent.getId());
            Shipment.setType(3);//出货单
            Shipment.setProfit(pmsProduct.getDeliveryAmount());
            Shipment.setPhone(wmsMember.getPhone());
            Shipment.setProductId(pmsProduct.getId());
            Integer ShipmentId= distributionMapper.insert(Shipment);
            //Shipment=distributionMapper.selectByParams(Shipment);
            //设置关联id
            distribution.setShipmentId(Shipment.getId().longValue());
            distributionMapper.insert(distribution);
        }
    }


    @Override
    public void shipment(Long id,Integer number){
         UmsMember currentMember = memberService.getCurrentMember();
         if(currentMember == null){
            Asserts.fail("用户未登录");
         }
         WmsMemberAreaDetail wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
         if(wmsMember==null){
            Asserts.fail("配送中心不存在");
         }
         //判断库存是否充足
         PmsSkuStock skuStock=new PmsSkuStock();
         skuStock.setId(id);
         skuStock=pmsSkuStockMapper.selectByParams(skuStock);
         if(skuStock==null){
             Asserts.fail("未找到库存");
         }
         if((skuStock.getStock()-skuStock.getLockStock()-number)<0){
             Asserts.fail("库存不足，无法出货");
         }
        skuStock.setStock(skuStock.getStock()-number);
         pmsSkuStockMapper.updateByPrimaryKey(skuStock);
    }

    @Override
    public WmsMember insertWmsMember(UmsMember umsMember,OmsOrderItem omsOrderItem){
        WmsMember wmsMember = new WmsMember();
        BeanUtils.copyProperties(umsMember,wmsMember);
        wmsMember.setUmsMemberId(umsMember.getId());
        wmsMember.setId(null);
        wmsMember.setParentId(1L);
        wmsMember.setLevel(1);
        wmsMember.setCreditLine(omsOrderItem.getProductPrice());
        wmsMemberMapper.insert(wmsMember);
        log.info("---------插入的仓储账号信息为:"+wmsMember);
        return wmsMember;
    }

}
