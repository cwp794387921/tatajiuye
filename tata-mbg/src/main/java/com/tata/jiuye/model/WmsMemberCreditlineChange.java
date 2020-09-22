package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WmsMemberCreditlineChange implements Serializable {
    private Long id;

    @ApiModelProperty(value = "配送用户id")
    private Long wmsMemberId;

    @ApiModelProperty(value = "变动值")
    private BigDecimal changeValue;

    @ApiModelProperty(value = "变更前")
    private BigDecimal beforeValue;

    @ApiModelProperty(value = "变更后")
    private BigDecimal afterValue;

    @ApiModelProperty(value = "备注")
    private String remark;

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

    public BigDecimal getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(BigDecimal changeValue) {
        this.changeValue = changeValue;
    }

    public BigDecimal getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(BigDecimal beforeValue) {
        this.beforeValue = beforeValue;
    }

    public BigDecimal getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(BigDecimal afterValue) {
        this.afterValue = afterValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        sb.append(", changeValue=").append(changeValue);
        sb.append(", beforeValue=").append(beforeValue);
        sb.append(", afterValue=").append(afterValue);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}