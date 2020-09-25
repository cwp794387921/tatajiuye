package com.tata.jiuye.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tata.jiuye.model.OmsDistribution;
import com.tata.jiuye.model.OmsDistributionItem;
import com.tata.jiuye.model.OmsOrderItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 明细详情(流水详情)
 */
@Data
public class OmsOrderDetailDTO extends OmsDistribution {

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "流水状态-> 待入账, 已入账")
    private String flowStatus;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "订单类型 ")
    private String orderType;

    @ApiModelProperty(value = "订单商品")
    List<OmsOrderItem> orderItems;

    @ApiModelProperty(value = "补货单/配送单商品")
    List<OmsDistributionItem> distributionItems;


    @ApiModelProperty(value = "关联订单号")
    private String orderSn;

    @ApiModelProperty(value = "状态 0待配送 1配送完成")
    private Integer status;

    private Integer statusNo1;
    private Integer statusNo2;
    private Integer statusNo3;

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

    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
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

    @ApiModelProperty(value = "补货单对应审核id")
    private Long replenishableId;

    @ApiModelProperty(value = "补货单对应出货单id")
    private Long shipmentId;

    private String wmsPhone;
}
