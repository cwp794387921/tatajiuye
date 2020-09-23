package com.tata.jiuye.DTO;

import com.tata.jiuye.model.OmsDistributionItem;
import com.tata.jiuye.model.OmsOrderItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 明细详情(流水详情)
 */
@Data
public class OmsOrderDetailDTO {

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "流水状态-> 待入账, 已入账")
    private String flowStatus;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "订单商品")
    List<OmsOrderItem> orderItems;

    @ApiModelProperty(value = "补货单/配送单商品")
    List<OmsDistributionItem> distributionItems;
}
