package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AcctSettleInfo implements Serializable {
    @ApiModelProperty(value = "订单编号")
    private String orderId;

    private String acctId;

    private BigDecimal beforBal;

    private BigDecimal change;

    private String afterBal;

    private Date insertTime;

    private static final long serialVersionUID = 1L;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", acctId=").append(acctId);
        sb.append(", beforBal=").append(beforBal);
        sb.append(", change=").append(change);
        sb.append(", afterBal=").append(afterBal);
        sb.append(", insertTime=").append(insertTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}