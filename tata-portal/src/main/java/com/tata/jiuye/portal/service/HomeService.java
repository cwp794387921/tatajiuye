package com.tata.jiuye.portal.service;

import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.model.*;
import com.tata.jiuye.model.PmsProduct;
import com.tata.jiuye.model.PmsProductCategory;
import com.tata.jiuye.portal.domain.HomeContentResult;

import java.util.List;

/**
 * 首页内容管理Service
 * Created by macro on 2019/1/28.
 */
public interface HomeService {

    /**
     * 获取首页内容
     */
    HomeContentResult content();

    /**
     * 首页商品推荐
     */
    List<PmsProduct> recommendProductList(Integer pageSize, Integer pageNum);

    /**
     * 获取商品分类
     * @param parentId 0:获取一级分类；其他：获取指定二级分类
     */
    List<PmsProductCategory> getProductCateList(Long parentId);

    /**
     * 根据专题分类分页获取专题
     * @param cateId 专题分类id
     */
    List<CmsSubject> getSubjectList(Long cateId, Integer pageSize, Integer pageNum);

    /**
     * 分页获取人气推荐商品
     */
    List<PmsProduct> hotProductList(Integer pageNum, Integer pageSize);

    /**
     * 分页获取新品推荐商品
     */
    List<PmsProduct> newProductList(Integer pageNum, Integer pageSize);

    /**
     * 根据商品分类获取分页分类商品列表
     * @param pageNum                                       页码
     * @param pageSize                                      每页条数
     * @param productCategoryId                            商品分类ID
     * @author laich
     * @return                                               指定分类分页商品列表
     */
    CommonPage<PmsProduct> getPmsProductByProductCategoryId(Integer pageNum, Integer pageSize, Long productCategoryId);

    List<area> queryAllCityName();

    List<area> queryAllareaName(String city);
}
