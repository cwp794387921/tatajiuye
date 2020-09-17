package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class PmsSkuStockDetail extends PmsSkuStock {
    @ApiModelProperty(value = "商品名称")
    private String name;
    @ApiModelProperty(value = "副标题")
    private String subTitle;

    @ApiModelProperty(value = "商品描述")
    private String description;

    private BigDecimal deliveryCenterProductValue;
    private BigDecimal regionalProductValue;
    private BigDecimal webmasterProductValue;

    public BigDecimal getDeliveryCenterProductValue() {
        return deliveryCenterProductValue;
    }

    public void setDeliveryCenterProductValue(BigDecimal deliveryCenterProductValue) {
        this.deliveryCenterProductValue = deliveryCenterProductValue;
    }

    public BigDecimal getRegionalProductValue() {
        return regionalProductValue;
    }

    public void setRegionalProductValue(BigDecimal regionalProductValue) {
        this.regionalProductValue = regionalProductValue;
    }

    public BigDecimal getWebmasterProductValue() {
        return webmasterProductValue;
    }

    public void setWebmasterProductValue(BigDecimal webmasterProductValue) {
        this.webmasterProductValue = webmasterProductValue;
    }

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
