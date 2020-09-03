package com.tata.jiuye.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Inventory implements Serializable {
    @ApiModelProperty(value = "库存表ID")
    private Long id;

    @ApiModelProperty(value = "仓储用户ID")
    private Long wmsMemberId;

    @ApiModelProperty(value = "商品ID")
    private Long productId;

    @ApiModelProperty(value = "可用库存数量")
    private Integer availableInventoryQuantity;

    @ApiModelProperty(value = "锁定库存数量")
    private Integer lockedInventoryQuantity;

    @ApiModelProperty(value = "计量单位")
    private String unit;

    @ApiModelProperty(value = "补货单价")
    private BigDecimal replenishmentPrice;

    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getAvailableInventoryQuantity() {
        return availableInventoryQuantity;
    }

    public void setAvailableInventoryQuantity(Integer availableInventoryQuantity) {
        this.availableInventoryQuantity = availableInventoryQuantity;
    }

    public Integer getLockedInventoryQuantity() {
        return lockedInventoryQuantity;
    }

    public void setLockedInventoryQuantity(Integer lockedInventoryQuantity) {
        this.lockedInventoryQuantity = lockedInventoryQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getReplenishmentPrice() {
        return replenishmentPrice;
    }

    public void setReplenishmentPrice(BigDecimal replenishmentPrice) {
        this.replenishmentPrice = replenishmentPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", wmsMemberId=").append(wmsMemberId);
        sb.append(", productId=").append(productId);
        sb.append(", availableInventoryQuantity=").append(availableInventoryQuantity);
        sb.append(", lockedInventoryQuantity=").append(lockedInventoryQuantity);
        sb.append(", unit=").append(unit);
        sb.append(", replenishmentPrice=").append(replenishmentPrice);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
