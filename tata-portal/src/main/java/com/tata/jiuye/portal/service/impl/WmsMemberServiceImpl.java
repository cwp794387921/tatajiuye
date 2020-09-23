package com.tata.jiuye.portal.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.common.utils.OrderUtil;
import com.tata.jiuye.mapper.*;
import com.tata.jiuye.model.*;
import com.tata.jiuye.common.enums.FlowTypeEnum;
import com.tata.jiuye.portal.service.AcctSettleInfoService;
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
import java.util.*;


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
    private AcctSettleInfoService acctSettleInfoService;
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
    private OmsOrderItemMapper omsOrderItemMapper;
    @Resource
    private OmsOrderMapper orderMapper;
    @Resource
    private UmsMemberInviteRelationService umsMemberInviteRelationService;
    @Resource
    private WmsMemberCreditlineChangeMapper creditlineChangeMapper;
    @Resource
    private UmsMemberInviteRelationMapper umsMemberInviteRelationMapper;


    @Override
    public void creditLineChange(Long id,BigDecimal value,String remark){
        WmsMember wmsMember=wmsMemberMapper.selectByPrimaryKey(id);
        if (wmsMember==null){
            Asserts.fail("用户不存在");
        }
        WmsMemberCreditlineChange creditlineChange=new WmsMemberCreditlineChange();
        creditlineChange.setWmsMemberId(wmsMember.getId());
        creditlineChange.setBeforeValue(wmsMember.getCreditLine());
        creditlineChange.setChangeValue(value);
        creditlineChange.setAfterValue(wmsMember.getCreditLine().add(value));
        creditlineChange.setCreateTime(new Date());
        creditlineChange.setRemark(remark);
        creditlineChangeMapper.insert(creditlineChange);
    }


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
        result.put("wmsMemberInfo",wmsMember);
        result.put("bal",acctInfo.getBalance());
        result.put("lockBal",acctInfo.getLockAmount());
        result.put("creditLine",wmsMember.getCreditLine());
        /*//查找库存列表
        List<PmsSkuStockDetail> stockList=pmsSkuStockMapper.queryStockByMemberId(wmsMember.getId());
        result.put("stock",stockList);*/
        //查找补货单
        OmsDistribution distribution=new OmsDistribution();
        distribution.setStatusNo1(4);//取消
        distribution.setStatusNo2(5);//已完成
        distribution.setType(2);
        distribution.setWmsMemberId(wmsMember.getId());
        List<OmsDistribution> BhList=distributionMapper.queryList(distribution);
        result.put("bh",BhList);
        //查找出货单
        distribution.setType(3);
        List<OmsDistribution> JhList=distributionMapper.queryList(distribution);
        result.put("jh",JhList);
        return result;
    }

    @Override
    public List<WmsMemberAreaDetail> queryAllUser(Map<String,Object> params){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        WmsMember wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        params.put("id",wmsMember.getId());
        List<WmsMemberAreaDetail> memberAreaDetails=wmsMemberMapper.queryAllWmsUser(params);
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
        OmsDistribution omsDistribution=distributionMapper.selectByPrimaryKey(orderId);
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
    public void cancelReplenishable(Long id,String type){
        OmsDistribution omsDistribution=null;
        OmsDistribution shipment=null;
        WmsMember wmsMember=null;
        if(type.equals("1")){
            omsDistribution=distributionMapper.selectByPrimaryKey(id);
            wmsMember=wmsMemberMapper.selectByPrimaryKey(omsDistribution.getWmsMemberId());
            if(omsDistribution==null){
                Asserts.fail("补货单不存在");
            }
            if(omsDistribution.getStatus()!=0){
                Asserts.fail("已被接单不可取消");
            }
            //查找对应出货单
            shipment=distributionMapper.selectByPrimaryKey(omsDistribution.getShipmentId());
            if(shipment==null){
                Asserts.fail("出货单不存在");
            }
        }else if(type.equals("2")){
            shipment=distributionMapper.selectByPrimaryKey(id);
            if(shipment==null){
                Asserts.fail("出货单不存在");
            }
            if(shipment.getStatus()!=0){
                Asserts.fail("已接单不可取消");
            }
            //查找对应补货单
            omsDistribution=new OmsDistribution();
            omsDistribution.setShipmentId(shipment.getId().longValue());
            omsDistribution=distributionMapper.selectByParams(omsDistribution);
            if(omsDistribution==null){
                Asserts.fail("补货单不存在");
            }
            wmsMember=wmsMemberMapper.selectByPrimaryKey(omsDistribution.getWmsMemberId());
        }
        omsDistribution.setStatus(4);
        shipment.setStatus(4);
        wmsMember.setCreditLine(wmsMember.getCreditLine().add(omsDistribution.getPrice().multiply(new BigDecimal(omsDistribution.getNumber()))));
        wmsMemberMapper.updateByPrimaryKey(wmsMember);//返还授信额度
        distributionMapper.updateByPrimaryKey(omsDistribution);
        distributionMapper.updateByPrimaryKey(shipment);
    }

    @Override
    public void confirmOrder(Long orderId){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        OmsDistribution omsDistribution=new OmsDistribution();
        omsDistribution.setId(orderId);
        omsDistribution=distributionMapper.queryDistributionDetail(omsDistribution);
        if(omsDistribution==null){
            Asserts.fail("配送单不存在");
        }
        WmsMember wmsMember=wmsMemberMapper.selectByPrimaryKey(omsDistribution.getWmsMemberId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        omsDistribution.setStatus(5);//已完成
        distributionMapper.updateByPrimaryKey(omsDistribution);//更新配送单
        //更新订单状态
        OmsOrder order=orderMapper.selectByOrderNum(omsDistribution.getOrderSn());
        order.setStatus(2);
        order.setModifyTime(new Date());
        orderMapper.updateByPrimaryKey(order);
        //添加账户流水
        //acctSettleInfoService.insertAcctInfoChangeFlow(currentMember,order.getOrderSn());
        AcctInfo acctInfo=acctInfoMapper.selectByWmsId(wmsMember.getId());
        if (acctInfo==null){
            Asserts.fail("账户不存在");
        }
        AcctSettleInfo acctSettleInfo=new AcctSettleInfo();
        acctSettleInfo.setAcctId(acctInfo.getId());
        acctSettleInfo.setOmsDistributionNo(omsDistribution.getId());
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
    public void  cancelOrder(Long orderId){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        WmsMember wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        OmsDistribution omsDistribution=new OmsDistribution();
        omsDistribution.setId(orderId);
        omsDistribution=distributionMapper.queryDistributionDetail(omsDistribution);
        if(omsDistribution==null){
            Asserts.fail("配送单不存在");
        }
        //更新订单确认收货时间
        OmsOrder order=orderMapper.selectByOrderNum(omsDistribution.getOrderSn());
        order.setReceiveTime(null);
        orderMapper.updateByPrimaryKey(order);
        omsDistribution.setStatus(0);//待接单
        distributionMapper.updateByPrimaryKey(omsDistribution);
        List<OmsDistributionItem> list=omsDistribution.getItemList();
        for(OmsDistributionItem item :list){
            //减少授信额度
            PmsProduct pmsProduct=pmsProductMapper.selectByPrimaryKey(item.getProductId());
            if(pmsProduct==null){
                Asserts.fail("==>找不到商品信息");
            }
            String remark="取消接单减少额度,productId["+pmsProduct.getId()+"],number["+item.getNumber()+"]";
            switch (wmsMember.getLevel()){
                case 1:
                    wmsMember.setCreditLine(wmsMember.getCreditLine().subtract(
                            pmsProduct.getDeliveryCenterProductValue().multiply(new BigDecimal(item.getNumber()))));
                    creditLineChange(wmsMember.getId(),pmsProduct.getDeliveryCenterProductValue().
                            multiply(new BigDecimal(item.getNumber())).multiply(new BigDecimal(-1)),remark);
                    break;
                case 2:
                    wmsMember.setCreditLine(wmsMember.getCreditLine().subtract(
                            pmsProduct.getRegionalProductValue().multiply(new BigDecimal(item.getNumber()))));
                    creditLineChange(wmsMember.getId(),pmsProduct.getRegionalProductValue().
                            multiply(new BigDecimal(item.getNumber())).multiply(new BigDecimal(-1)),remark);
                    break;
                case 3:
                    wmsMember.setCreditLine(wmsMember.getCreditLine().subtract(
                            pmsProduct.getWebmasterProductValue().multiply(new BigDecimal(item.getNumber()))));
                    creditLineChange(wmsMember.getId(),pmsProduct.getWebmasterProductValue().
                            multiply(new BigDecimal(item.getNumber())).multiply(new BigDecimal(-1)),remark);
                    break;
            }
            wmsMember.setUpdateTime(new Date());
            wmsMemberMapper.updateByPrimaryKey(wmsMember);
        }
    }

    @Override
    public void  creditLineChange(Long memberId,String value){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        WmsMember wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        WmsMember changeInfo=wmsMemberMapper.selectByPrimaryKey(memberId);
        if(changeInfo==null){
            Asserts.fail("转让配送中心信息不存在");
        }
        BigDecimal changeValue=new BigDecimal(value);
        wmsMember.setCreditLine(wmsMember.getCreditLine().subtract(changeValue));
        wmsMember.setUpdateTime(new Date());
        changeInfo.setCreditLine(changeInfo.getCreditLine().add(changeValue));
        changeInfo.setUpdateTime(new Date());
        String remark=wmsMember.getId()+"转让额度["+changeValue+"]到"+changeInfo.getId();
        wmsMemberMapper.updateByPrimaryKey(wmsMember);
        //插入额度变动明细
        creditLineChange(wmsMember.getId(),changeValue.multiply(new BigDecimal(-1)),remark);
        wmsMemberMapper.updateByPrimaryKey(changeInfo);
        creditLineChange(changeInfo.getId(),changeValue,remark);
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
        OmsDistribution omsDistribution=new OmsDistribution();
        omsDistribution.setId(orderId);
        omsDistribution=distributionMapper.queryDistributionDetail(omsDistribution);
        if(omsDistribution==null){
            Asserts.fail("配送单不存在");
        }
        //更新订单确认收货时间
        OmsOrder order=orderMapper.selectByOrderNum(omsDistribution.getOrderSn());
        order.setReceiveTime(DateUtil.offset(new Date(), DateField.DAY_OF_MONTH, 3));
        orderMapper.updateByPrimaryKey(order);

        omsDistribution.setStatus(1);//待配送
        distributionMapper.updateByPrimaryKey(omsDistribution);
        List<OmsDistributionItem> list=omsDistribution.getItemList();
        for(OmsDistributionItem item :list){
        //增加授信额度
        PmsProduct pmsProduct=pmsProductMapper.selectByPrimaryKey(item.getProductId());
        if(pmsProduct==null){
            Asserts.fail("==>找不到商品信息");
        }
        String remark="接单增加额度,productId["+pmsProduct.getId()+"],number["+item.getNumber()+"]";
        switch (wmsMember.getLevel()){
            case 1:
                wmsMember.setCreditLine(wmsMember.getCreditLine().add(
                        pmsProduct.getDeliveryCenterProductValue().multiply(new BigDecimal(item.getNumber()))));
                creditLineChange(wmsMember.getId(),pmsProduct.getDeliveryCenterProductValue().multiply(new BigDecimal(item.getNumber())),remark);
                break;
            case 2:
                wmsMember.setCreditLine(wmsMember.getCreditLine().add(
                        pmsProduct.getRegionalProductValue().multiply(new BigDecimal(item.getNumber()))));
                creditLineChange(wmsMember.getId(),pmsProduct.getRegionalProductValue().multiply(new BigDecimal(item.getNumber())),remark);
                break;
            case 3:
                wmsMember.setCreditLine(wmsMember.getCreditLine().add(
                        pmsProduct.getWebmasterProductValue().multiply(new BigDecimal(item.getNumber()))));
                creditLineChange(wmsMember.getId(),pmsProduct.getWebmasterProductValue().multiply(new BigDecimal(item.getNumber())),remark);
                break;
        }
        wmsMember.setUpdateTime(new Date());
        wmsMemberMapper.updateByPrimaryKey(wmsMember);
        }
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
        OmsDistribution omsDistribution=distributionMapper.selectByPrimaryKey(orderId);
        if(omsDistribution==null){
            Asserts.fail("配送单不存在");
        }
        omsDistribution.setStatus(5);//已完成
        distributionMapper.updateByPrimaryKey(omsDistribution);//更新配送单
        //返还补货额度
        PmsProduct pmsProduct=pmsProductMapper.selectByPrimaryKey(omsDistribution.getProductId());
        switch (wmsMember.getLevel()){
            case 1:
                wmsMember.setCreditLine(wmsMember.getCreditLine().add(pmsProduct.getDeliveryCenterProductValue()));
                break;
            case 2:
                wmsMember.setCreditLine(wmsMember.getCreditLine().add(pmsProduct.getRegionalProductValue()));
                break;
            case 3:
                wmsMember.setCreditLine(wmsMember.getCreditLine().add(pmsProduct.getWebmasterProductValue()));
                break;
        }
        wmsMemberMapper.updateByPrimaryKey(wmsMember);
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
        //更新商品配送状态信息
        Map<String,Object>params=new HashMap<>();
        params.put("relationDistributionId",omsDistribution.getId());
        OmsOrderItem orderItem=omsOrderItemMapper.selectByParams(params);
        if(orderItem==null){
            Asserts.fail("未找到订单详情");
        }
        orderItem.setDistributionStatus(2L);//送达
        omsOrderItemMapper.updateByPrimaryKey(orderItem);
        params=new HashMap<>();
        params.put("orderId",orderItem.getOrderId());
        params.put("statusNo",2);
        List<OmsOrderItem> list=omsOrderItemMapper.queryList(params);
        if(null == list || list.size() ==0){
            OmsOrder order=orderMapper.selectByPrimaryKey(orderItem.getOrderId());
            order.setStatus(2);
            order.setModifyTime(new Date());
            orderMapper.updateByPrimaryKey(order);
        }
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
        OmsDistribution distribution=distributionMapper.selectByPrimaryKey(id);
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
            PmsProduct pmsProduct=pmsProductMapper.selectByPrimaryKey(id);
            if(pmsProduct==null){
                Asserts.fail("商品不存在");
            }
            int num=param.getNumber();
            BigDecimal price=BigDecimal.ZERO;//补货货值
            BigDecimal CHprice=BigDecimal.ZERO;//出货货值
            BigDecimal profit=BigDecimal.ZERO;//仓补收益
            BigDecimal CHprofit=BigDecimal.ZERO;//出货收益
            switch (wmsMember.getLevel()){
                case 1:
                    profit=pmsProduct.getDeliveryCenterWarehouseReplenishment();
                    price=pmsProduct.getDeliveryCenterProductValue();
                    break;
                case 2:
                    profit=pmsProduct.getRegionalWarehouseReplenishment();
                    price=pmsProduct.getRegionalProductValue();
                    break;
                case 3:
                    profit=pmsProduct.getWebmasterWarehouseReplenishment();
                    price=pmsProduct.getWebmasterProductValue();
                    break;
            }
            switch (parent.getLevel()){
                case 1:
                    CHprofit=pmsProduct.getDeliveryCenterWarehouseReplenishment();
                    CHprice=pmsProduct.getDeliveryCenterProductValue();
                    break;
                case 2:
                    CHprofit=pmsProduct.getRegionalWarehouseReplenishment();
                    CHprice=pmsProduct.getRegionalProductValue();
                    break;
                case 3:
                    CHprofit=pmsProduct.getWebmasterWarehouseReplenishment();
                    CHprice=pmsProduct.getWebmasterProductValue();
                    break;
            }
            subPrice=subPrice.add(new BigDecimal(num).multiply(price));
            if(subPrice.compareTo(wmsMember.getCreditLine())==1){
                Asserts.fail("补货总价超过授信额度");
            }
            //生成补货单
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
            Shipment.setPrice(CHprice);
            Shipment.setNumber(num);
            Shipment.setSubPrice(CHprice.multiply(new BigDecimal(num)));
            Shipment.setName(wmsMember.getNickname());
            Shipment.setHeadImg(wmsMember.getIcon());
            Shipment.setAddress(wmsMember.getAddress());
            Shipment.setCreateTime(new Date());
            Shipment.setWmsMemberId(parent.getId());
            Shipment.setType(3);//出货单
            Shipment.setProfit(CHprofit.multiply(new BigDecimal(num)));//出货收益
            Shipment.setPhone(wmsMember.getPhone());
            Shipment.setProductId(pmsProduct.getId());
            Integer ShipmentId= distributionMapper.insert(Shipment);
            //Shipment=distributionMapper.selectByParams(Shipment);
            //设置关联id
            distribution.setShipmentId(Shipment.getId().longValue());
            distributionMapper.insert(distribution);
        }
        log.info("==>总货值["+subPrice+"]");
        String remark=wmsMember.getId()+"补货扣减额度["+subPrice+"]";
        wmsMember.setCreditLine(wmsMember.getCreditLine().subtract(subPrice));
        wmsMemberMapper.updateByPrimaryKey(wmsMember);//扣减授信额度
        creditLineChange(wmsMember.getId(),subPrice.multiply(new BigDecimal(-1)),remark);
    }


    @Override
    public void CHshipment(Long id){
        UmsMember currentMember = memberService.getCurrentMember();
        if(currentMember == null){
            Asserts.fail("用户未登录");
        }
        WmsMemberAreaDetail wmsMember=wmsMemberMapper.selectByUmsId(currentMember.getId());
        if(wmsMember==null){
            Asserts.fail("配送中心不存在");
        }
        OmsDistribution shipment=distributionMapper.selectByPrimaryKey(id);
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
        skuStock.setWmsMemberId(wmsMember.getId());
        skuStock=pmsSkuStockMapper.selectByParams(skuStock);
        if(skuStock==null){
            Asserts.fail("未找到库存");
        }
        if((skuStock.getStock()-number)<0){
            Asserts.fail("库存不足，无法出货");
        }
        //添加锁定库存
        skuStock.setLockStock(skuStock.getLockStock()+number);
        pmsSkuStockMapper.updateByPrimaryKey(skuStock);
        //更新补货单状态
        distribution.setStatus(1);//待收货
        //更新出货单状态
        shipment.setStatus(1);//待收货
        distributionMapper.updateByPrimaryKey(distribution);
        distributionMapper.updateByPrimaryKey(shipment);

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
    public WmsMember insertWmsMember(UmsMember umsMember,BigDecimal creditLine){
        WmsMember wmsMember = new WmsMember();
        BeanUtils.copyProperties(umsMember,wmsMember);
        wmsMember.setUmsMemberId(umsMember.getId());
        wmsMember.setId(null);
        wmsMember.setParentId(1L);
        wmsMember.setLevel(1);
        wmsMember.setCreditLine(creditLine);
        wmsMemberMapper.insert(wmsMember);
        log.info("---------插入的仓储账号信息为:"+wmsMember);
        return wmsMember;
    }

}
