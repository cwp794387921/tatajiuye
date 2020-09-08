package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class ChangeDistribution implements Serializable {
    private Long id;

    @ApiModelProperty(value = "配送用户id")
    private Long wmsMemberId;

    @ApiModelProperty(value = "转让配送用户id")
    private Long changeMemberId;

    @ApiModelProperty(value = "配送订单号")
    private String orderSn;

    @ApiModelProperty(value = "插入时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWmsMemberId() {
        return wmsMemberId;
    }

    public void setWmsMemberId(Long wmsMemberId) {
        this.wmsMemberId = wmsMemberId;
    }

    public Long getChangeMemberId() {
        return changeMemberId;
    }

    public void setChangeMemberId(Long changeMemberId) {
        this.changeMemberId = changeMemberId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", wmsMemberId=").append(wmsMemberId);
        sb.append(", changeMemberId=").append(changeMemberId);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}