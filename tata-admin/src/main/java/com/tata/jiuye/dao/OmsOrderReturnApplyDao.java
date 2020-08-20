package com.tata.jiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.dto.OmsOrderReturnApplyResult;
import com.tata.jiuye.dto.OmsReturnApplyQueryParam;
import com.tata.jiuye.model.OmsOrderReturnApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单退货申请自定义Mapper
 *
 * @author lewis
 */
public interface OmsOrderReturnApplyDao extends BaseMapper<OmsOrderReturnApply> {

    /**
     * 查询申请列表
     */
    List<OmsOrderReturnApply> getList(@Param("queryParam") OmsReturnApplyQueryParam queryParam);

    /**
     * 获取申请详情
     */
    OmsOrderReturnApplyResult getDetail(@Param("id")Long id);
}
