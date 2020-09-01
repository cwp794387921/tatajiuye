package com.tata.jiuye.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.AcctSettleInfo;
import org.springframework.transaction.annotation.Transactional;

public interface AcctSettleInfoService extends IService<AcctSettleInfo> {

    /**
     *  新增/修改 账户流水记录表
     * @param acctSettleInfo
     */
    @Transactional
    void saveOrUpdateAcctSettleInfo(AcctSettleInfo acctSettleInfo);
}
