package com.tata.jiuye.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.common.enums.FlowTypeEnum;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.*;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.service.UmsMemberService;
import com.tata.jiuye.portal.service.WmsAreaService;
import com.tata.jiuye.portal.service.WmsMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class WmsAreaServiceImpl extends ServiceImpl<WmsAreaMapper,WmsArea> implements WmsAreaService {

    @Autowired
    private WmsAreaMapper wmsAreaMapper;

    @Override
    public void insertWmsArea(OmsOrder omsOrder,Long wmsMemberId) {
        if(omsOrder == null){
            Asserts.fail("订单信息为空");
        }
        WmsArea wmsArea = new WmsArea();
        wmsArea.setAddress(omsOrder.getReceiverDetailAddress());
        wmsArea.setProvince(omsOrder.getReceiverProvince());
        wmsArea.setCity(omsOrder.getReceiverCity());
        wmsArea.setArea(omsOrder.getReceiverRegion());
        wmsArea.setStatus(1);
        wmsArea.setWmsMemberId(wmsMemberId);
        this.saveOrUpdate(wmsArea);
    }

    @Override
    public WmsArea getByMemberId(Long wmsMemberId){
        if(wmsMemberId == null){
            Asserts.fail("配送中心用户ID不能为空");
        }
        WmsAreaExample wmsAreaExample = new WmsAreaExample();
        WmsAreaExample.Criteria criteria = wmsAreaExample.createCriteria();
        criteria.andWmsMemberIdEqualTo(wmsMemberId);
        List<WmsArea> wmsAreas = wmsAreaMapper.selectByExample(wmsAreaExample);
        if(CollectionUtils.isEmpty(wmsAreas)){
            return null;
        }
        return wmsAreas.get(0);
    }

    @Override
    public void updateWmsArea(WmsArea wmsArea){
        this.saveOrUpdate(wmsArea);
    }


    @Override
    public void delByWmsMemberId(Long wmsMemberId){
        WmsAreaExample wmsAreaExample = new WmsAreaExample();
        WmsAreaExample.Criteria criteria = wmsAreaExample.createCriteria();
        criteria.andWmsMemberIdEqualTo(wmsMemberId);
        wmsAreaMapper.deleteByExample(wmsAreaExample);
    }
}
