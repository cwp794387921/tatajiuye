package com.tata.jiuye.portal.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 购物车中选择规格的商品信息
 * Created by macro on 2018/8/2.
 */
@Data
public class PmsProductQueryByCategoryResult  {
    @ApiModelProperty("商品ID")
    private Long productId;

    @ApiModelProperty("商品分类ID")
    private Long productCategoryId;

    @ApiModelProperty(value = "商品分类名称")
    private String productCategoryName;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品主图")
    private String pic;


}
