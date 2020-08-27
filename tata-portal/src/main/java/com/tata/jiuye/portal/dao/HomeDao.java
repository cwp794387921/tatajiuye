package com.tata.jiuye.portal.dao;

import com.tata.jiuye.model.CmsSubject;
import com.tata.jiuye.model.PmsBrand;
import com.tata.jiuye.model.PmsProduct;
import com.tata.jiuye.portal.domain.FlashPromotionProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 首页内容管理自定义Dao
 * Created by macro on 2019/1/28.
 */
public interface HomeDao {

    /**
     * 获取推荐品牌
     */



    List<PmsBrand> getRecommendBrandList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取秒杀商品
     */
    List<FlashPromotionProduct> getFlashProductList(@Param("flashPromotionId") Long flashPromotionId, @Param("sessionId") Long sessionId);

    /**
     * 获取新品推荐
     */
    List<PmsProduct> getNewProductList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取人气推荐
     */
    List<PmsProduct> getHotProductList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取推荐专题
     */
    List<CmsSubject> getRecommendSubjectList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 通过商品分类ID获取分页分类商品列表
     *
     * @param productCategoryId
     * @return
     */
    List<PmsProduct> getPmsProductByProductCategoryId(@Param("productCategoryId") Long productCategoryId);
}
