package com.tata.jiuye.dto;

import com.tata.jiuye.model.OmsOrder;
import com.tata.jiuye.model.OmsOrderItem;
import com.tata.jiuye.model.OmsOrderOperateHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 订单详情信息
 *
 * @author lewis
 */
@Data
public class OmsOrderDetail extends OmsOrder {

    @ApiModelProperty("配送单号")
    private Long omsDistributionNo;

    @ApiModelProperty("配送中心ID(接单人)")
    private Long wmsMemberId;

    @ApiModelProperty("配送中心昵称(接单人)")
    private String wmsMemberNickName;

    @ApiModelProperty("订单商品列表")
    private List<OmsOrderItem> orderItemList;
    @ApiModelProperty("订单操作记录列表")
    private List<OmsOrderOperateHistory> historyList;
}
