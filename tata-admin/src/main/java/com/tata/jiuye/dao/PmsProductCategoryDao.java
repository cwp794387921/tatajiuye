package com.tata.jiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.dto.PmsProductCategoryWithChildrenItem;
import com.tata.jiuye.model.PmsProductCategory;

import java.util.List;

/**
 * 商品分类自定义Mapper
 *
 * @author lewis
 */
public interface PmsProductCategoryDao extends BaseMapper<PmsProductCategory> {

    /**
     * 获取商品分类及其子分类
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
