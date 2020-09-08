package com.tata.jiuye.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.DTO.WithdrawExamineQueryParam;
import com.tata.jiuye.DTO.WithdrawExamineQueryResult;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.model.UmsAdmin;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.model.WithdrawalExamine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface WithdrawalExamineService extends IService<WithdrawalExamine> {

    /**
     * 插入提现申请(小程序端用)
     * @param umsMember                     申请人用户信息
     * @param withdrawAmount                申请提现金额
     * @param accountType                   申请提现的账户类型
     */
    @Transactional
    void insertWithdrawalExamine(UmsMember umsMember, BigDecimal withdrawAmount,String accountType);


    /**
     * 提现审批(后台用)
     * @param umsAdminMemberId                       审批人用户ID(umsAdmin)
     * @param withdrExamineId                        提现申请表ID
     * @param operateType                             操作类型(PASS->通过,REFUSE->拒绝)
     */
    @Transactional
    void passOrRejectWithdrawalExamine(Long umsAdminMemberId,String umsAdminNickName, Long withdrExamineId, String operateType);


    /**
     * 获取我的提现申请列表(小程序端用)
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @return
     */
    CommonPage<WithdrawExamineQueryResult> getMyWithdrawalExaminePage(Integer pageNum, Integer pageSize, Long memberId);

    /**
     * 通过查询条件获取提现申请表数据(后台用)
     * @param withdrawExamineQueryParam
     * @return
     */
    CommonPage<WithdrawExamineQueryResult> getAllWithdrawalExaminePageByQueryParam(WithdrawExamineQueryParam withdrawExamineQueryParam);
}
