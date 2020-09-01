package com.tata.jiuye.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AcctSettleInfo implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderId;

    @ApiModelProperty(value = "账户ID")
    private String acctId;

    @ApiModelProperty(value = "变动前金额")
    private BigDecimal beforBal;

    @ApiModelProperty(value = "变动金额")
    private BigDecimal change;

    @ApiModelProperty(value = "变动后金额")
    private String afterBal;

    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "变动时间")
    private Date insertTime;

    @ApiModelProperty(value = "流水类型(收入-> income,支出-> expenditure)")
    private String flowType;

    @ApiModelProperty(value = "流水类型明细(收入->(分佣收入 -> commission_income,配送费 -> delivery_fee,仓储补助->storage _allowance),支出->(提现->withdraw))")
    private String flowTypeDetail;

    @ApiModelProperty(value = "流水来源ID(收入专用)")
    private Long sourceId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public BigDecimal getBeforBal() {
        return beforBal;
    }

    public void setBeforBal(BigDecimal beforBal) {
        this.beforBal = beforBal;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public String getAfterBal() {
        return afterBal;
    }

    public void setAfterBal(String afterBal) {
        this.afterBal = afterBal;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getFlowTypeDetail() {
        return flowTypeDetail;
    }

    public void setFlowTypeDetail(String flowTypeDetail) {
        this.flowTypeDetail = flowTypeDetail;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", acctId=").append(acctId);
        sb.append(", beforBal=").append(beforBal);
        sb.append(", change=").append(change);
        sb.append(", afterBal=").append(afterBal);
        sb.append(", insertTime=").append(insertTime);
        sb.append(", flowType=").append(flowType);
        sb.append(", flowTypeDetail=").append(flowTypeDetail);
        sb.append(", sourceId=").append(sourceId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
