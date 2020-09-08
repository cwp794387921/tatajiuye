package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OmsDistribution implements Serializable {
    @ApiModelProperty(value = "自增id")
    private Integer id;

    @ApiModelProperty(value = "关联订单号")
    private String orderSn;

    @ApiModelProperty(value = "状态 0待配送 1配送完成")
    private Integer status;

    @ApiModelProperty(value = "商品图片 ")
    private String goodsImg;

    @ApiModelProperty(value = "商品标题")
    private String goodsTitle;

    @ApiModelProperty(value = "商品副标题")
    private String goodsSubtitle;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "件数")
    private Integer number;

    @ApiModelProperty(value = "总价")
    private BigDecimal subPrice;

    @ApiModelProperty(value = "收货人名称")
    private String name;

    @ApiModelProperty(value = "收货人头像")
    private String headImg;

    @ApiModelProperty(value = "收货人地址")
    private String address;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "配送用户id")
    private Long wmsMemberId;

    @ApiModelProperty(value = "类型 1配送单 2补货单  3借货单")
    private Integer type;

    @ApiModelProperty(value = "配送收益")
    private BigDecimal profit;

    private String phone;

    private Long umsMemberId;

    private Long productId;

    private static final long serialVersionUID = 1L;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUmsMemberId() {
        return umsMemberId;
    }

    public void setUmsMemberId(Long umsMemberId) {
        this.umsMemberId = umsMemberId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsSubtitle() {
        return goodsSubtitle;
    }

    public void setGoodsSubtitle(String goodsSubtitle) {
        this.goodsSubtitle = goodsSubtitle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(BigDecimal subPrice) {
        this.subPrice = subPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getWmsMemberId() {
        return wmsMemberId;
    }

    public void setWmsMemberId(Long wmsMemberId) {
        this.wmsMemberId = wmsMemberId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", status=").append(status);
        sb.append(", goodsImg=").append(goodsImg);
        sb.append(", goodsTitle=").append(goodsTitle);
        sb.append(", goodsSubtitle=").append(goodsSubtitle);
        sb.append(", price=").append(price);
        sb.append(", number=").append(number);
        sb.append(", subPrice=").append(subPrice);
        sb.append(", name=").append(name);
        sb.append(", headImg=").append(headImg);
        sb.append(", address=").append(address);
        sb.append(", createTime=").append(createTime);
        sb.append(", wmsMemberId=").append(wmsMemberId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}