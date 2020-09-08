package com.tata.jiuye.portal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.*;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.common.constant.FlowTypeEnum;
import com.tata.jiuye.portal.service.UmsMemberService;
import com.tata.jiuye.portal.service.WmsMerberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class WmsMemberServiceImpl implements WmsMerberService {

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
        distribution.setStatus(0);
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
        pmsSkuStock.setWmsMemberId(wmsMember.getId().intValue());
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
        distributionMapper.updateByPrimaryKey(omsDistribution);
        //清除锁定库存
        PmsSkuStock pmsSkuStock=new PmsSkuStock();
        pmsSkuStock.setProductId(omsDistribution.getProductId());
        pmsSkuStock.setWmsMemberId(wmsMember.getId().intValue());
        pmsSkuStock=pmsSkuStockMapper.selectByParams(pmsSkuStock);
        if(pmsSkuStock==null){
            Asserts.fail("库存不存在");
        }
        pmsSkuStock.setLockStock(pmsSkuStock.getLockStock()-omsDistribution.getNumber());//减少锁定库存
        pmsSkuStock.setStock(pmsSkuStock.getStock()-omsDistribution.getNumber());//减少实际库存
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
        acctSettleInfoMapper.insert(acctSettleInfo);
    }


}
