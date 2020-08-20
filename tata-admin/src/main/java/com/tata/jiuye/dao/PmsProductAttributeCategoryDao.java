package com.tata.jiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.dto.PmsProductAttributeCategoryItem;
import com.tata.jiuye.model.PmsProductAttributeCategory;

import java.util.List;

/**
 * 自定义商品属性分类Dao
 *
 * @author lewis
 */
public interface PmsProductAttributeCategoryDao extends BaseMapper<PmsProductAttributeCategory> {

    /**
     * 获取包含属性的商品属性分类
     */
    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
