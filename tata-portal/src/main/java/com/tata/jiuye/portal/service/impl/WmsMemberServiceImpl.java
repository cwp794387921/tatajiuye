package com.tata.jiuye.portal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.AcctInfoMapper;
import com.tata.jiuye.mapper.OmsDistributionMapper;
import com.tata.jiuye.mapper.PmsSkuStockMapper;
import com.tata.jiuye.mapper.WmsMemberMapper;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.service.PmsSkuStockService;
import com.tata.jiuye.portal.service.UmsMemberService;
import com.tata.jiuye.portal.service.WmsMerberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private OmsDistributionMapper distributionMapper;
    @Resource
    private PmsSkuStockMapper pmsSkuStockMapper;


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

}
