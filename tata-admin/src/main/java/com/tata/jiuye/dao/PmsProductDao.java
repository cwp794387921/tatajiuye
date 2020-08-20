package com.tata.jiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.dto.PmsProductResult;
import com.tata.jiuye.model.PmsProduct;
import org.apache.ibatis.annotations.Param;


/**
 * 自定义商品管理Mapper
 *
 * @author lewis
 */
public interface PmsProductDao extends BaseMapper<PmsProduct> {

    /**
     * 获取商品编辑信息
     */
    PmsProductResult getUpdateInfo(@Param("id") Long id);
}
