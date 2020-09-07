package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;

public class PmsSkuStockDetail extends PmsSkuStock {
    @ApiModelProperty(value = "商品名称")
    private String name;
    @ApiModelProperty(value = "副标题")
    private String subTitle;

    @ApiModelProperty(value = "商品描述")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
