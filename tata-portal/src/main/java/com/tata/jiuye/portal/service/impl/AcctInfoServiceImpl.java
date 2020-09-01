package com.tata.jiuye.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.mapper.AcctInfoMapper;
import com.tata.jiuye.model.AcctInfo;
import com.tata.jiuye.portal.service.AcctInfoService;
import com.tata.jiuye.portal.service.AcctSettleInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 账户表相关业务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AcctInfoServiceImpl extends ServiceImpl<AcctInfoMapper, AcctInfo> implements AcctInfoService {


    @Override
    public void saveOrUpdateAcctInfo(AcctInfo acctInfo){
        this.saveOrUpdate(acctInfo);
    }
}
