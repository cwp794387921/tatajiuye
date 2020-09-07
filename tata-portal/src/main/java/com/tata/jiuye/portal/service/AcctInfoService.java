package com.tata.jiuye.portal.service;

import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.AcctInfo;
import com.tata.jiuye.model.AcctSettleInfo;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

public interface AcctInfoService extends IService<AcctInfo> {


    /**
     * 增/改账户表信息
     * @param acctInfo
     */
    @Transactional
    void saveOrUpdateAcctInfo(AcctInfo acctInfo);

    /**
     * 通过用户ID获取账户信息
     * @param memberId
     * @return
     */
    AcctInfo getAcctInfoByMemberId(Long memberId);

    /**
     * 金额变动更新表
     * @param acctMemberId              变动金额的账户所属用户ID
     * @param changeAmount              变动金额
     * @param type                       变动状态(收入 -> income,支出 -> expenditure)
     */
    @Transactional
    AcctSettleInfo updateAcctInfoByAmount(Long acctMemberId, BigDecimal changeAmount, String type);


}
