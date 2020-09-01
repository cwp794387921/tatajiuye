package com.tata.jiuye.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.AcctInfo;
import org.springframework.transaction.annotation.Transactional;

public interface AcctInfoService extends IService<AcctInfo> {


    /**
     * 增/改账户表信息
     * @param acctInfo
     */
    @Transactional
    void saveOrUpdateAcctInfo(AcctInfo acctInfo);
}
