package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class ReplenishableExamine implements Serializable {
    private Long id;

    @ApiModelProperty(value = "申请人id（配送中心id）")
    private Long applyId;

    @ApiModelProperty(value = "申请人姓名")
    private String applyName;

    @ApiModelProperty(value = "申请时间")
    private Date createTime;

    @ApiModelProperty(value = "审批人id")
    private Long approvalId;

    @ApiModelProperty(value = "审批人姓名")
    private Long approvalName;

    @ApiModelProperty(value = "状态 0待审核 1待收货  2审核完成  3驳回")
    private Integer status;

    @ApiModelProperty(value = "审核时间")
    private Date updateTime;

    @ApiModelProperty(value = "对应交易流水")
    private Long transSn;

    @ApiModelProperty(value = "补货商品id")
    private Long productId;

    @ApiModelProperty(value = "补货数量")
    private Integer number;

    private String imgs;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Long approvalId) {
        this.approvalId = approvalId;
    }

    public Long getApprovalName() {
        return approvalName;
    }

    public void setApprovalName(Long approvalName) {
        this.approvalName = approvalName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Long getTransSn() {
        return transSn;
    }

    public void setTransSn(Long transSn) {
        this.transSn = transSn;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applyId=").append(applyId);
        sb.append(", applyName=").append(applyName);
        sb.append(", createTime=").append(createTime);
        sb.append(", approvalId=").append(approvalId);
        sb.append(", approvalName=").append(approvalName);
        sb.append(", status=").append(status);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", transSn=").append(transSn);
        sb.append(", productId=").append(productId);
        sb.append(", number=").append(number);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}