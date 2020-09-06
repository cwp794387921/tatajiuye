package com.tata.jiuye.portal.domain;

import com.tata.jiuye.model.CmsSubject;
import com.tata.jiuye.model.PmsBrand;
import com.tata.jiuye.model.PmsProduct;
import com.tata.jiuye.model.SmsHomeAdvertise;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 首页内容返回信息封装
 * Created by macro on 2019/1/28.
 */
@Getter
@Setter
public class HomeContentResult {
    /**
     * 轮播广告
     */
    @ApiModelProperty("轮播广告")
    private List<SmsHomeAdvertise> advertiseList;

    /**
     * 升级为VIP的商品分类ID集合
     */
    @ApiModelProperty("升级为VIP的商品分类ID集合")
    private List<Long> upgradeToVipProductCategoryIds;

    /**
     * 升级为配置中心的商品分类ID集合
     */
    @ApiModelProperty("升级为配置中心的商品分类ID集合")
    private List<Long> upgradeDistributionCenterCategoryIds;

    /**
     * 会员复购ID
     */
    @ApiModelProperty("会员复购ID")
    private Long productCategoryId;
    //推荐品牌
    //private List<PmsBrand> brandList;
    //当前秒杀场次
    //private HomeFlashPromotion homeFlashPromotion;
    //人气推荐(热卖)
    private List<PmsProduct> hotProductList;
    //新品推荐
    private List<PmsProduct> newProductList;
    //推荐专题
    //private List<CmsSubject> subjectList;
}
