package com.tata.jiuye.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.mapper.AcctSettleInfoMapper;
import com.tata.jiuye.model.AcctSettleInfo;
import com.tata.jiuye.portal.service.AcctSettleInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 账户流水相关业务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AcctSettleInfoServiceImpl extends ServiceImpl<AcctSettleInfoMapper, AcctSettleInfo> implements AcctSettleInfoService {


    @Override
    public void saveOrUpdateAcctSettleInfo(AcctSettleInfo acctSettleInfo){
        this.saveOrUpdate(acctSettleInfo);
    }
}
