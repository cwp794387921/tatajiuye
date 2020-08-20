package com.tata.jiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.dto.SmsCouponParam;
import com.tata.jiuye.model.SmsCoupon;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义优惠券管理Dao
 *
 * @author lewis
 */
public interface SmsCouponDao extends BaseMapper<SmsCoupon> {

    /**
     * 获取优惠券详情包括绑定关系
     */
    SmsCouponParam getItem(@Param("id") Long id);
}
