package com.tata.jiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.dto.OmsOrderReturnApplyResult;
import com.tata.jiuye.dto.OmsReturnApplyQueryParam;
import com.tata.jiuye.dto.OmsUpdateStatusParam;
import com.tata.jiuye.model.OmsOrderReturnApply;

import java.util.List;

/**
 * 退货申请管理Service
 *
 * @author lewis
 */
public interface OmsOrderReturnApplyService extends IService<OmsOrderReturnApply> {

    /**
     * 分页查询申请
     */
    List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum);

    /**
     * 批量删除申请
     */
    int delete(List<Long> ids);

    /**
     * 修改申请状态
     */
    int updateStatus(Long id, OmsUpdateStatusParam statusParam);

    /**
     * 获取指定申请详情
     */
    OmsOrderReturnApplyResult getItem(Long id);
}
