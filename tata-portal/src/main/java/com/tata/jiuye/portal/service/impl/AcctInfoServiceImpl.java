package com.tata.jiuye.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.AcctInfoMapper;
import com.tata.jiuye.model.AcctInfo;
import com.tata.jiuye.portal.service.AcctInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 账户表相关业务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AcctInfoServiceImpl extends ServiceImpl<AcctInfoMapper, AcctInfo> implements AcctInfoService {
    @Autowired
    private AcctInfoMapper acctInfoMapper;

    @Override
    public void saveOrUpdateAcctInfo(AcctInfo acctInfo){
        this.saveOrUpdate(acctInfo);
    }

    @Override
    public AcctInfo getAcctInfoByMemberId(Long memberId){
        log.info("--------------------通过用户ID获取账户信息  开始--------------------");
        if(memberId == null){
            Asserts.fail("用户ID为空");
        }
        log.info("--------------------通过用户ID获取账户信息  结束--------------------");
       return acctInfoMapper.getByMemberId(memberId);
    }
}
