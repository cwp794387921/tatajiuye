package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class PmsSkuStockChangeFlow implements Serializable {
    @ApiModelProperty(value = "库存变动记录表")
    private Long id;

    @ApiModelProperty(value = "库存ID")
    private Long pmsSkuStockId;

    @ApiModelProperty(value = "变动数量")
    private Integer changeNum;

    @ApiModelProperty(value = "变动前数量")
    private Integer quantityBeforeChange;

    @ApiModelProperty(value = "变动后数量")
    private Integer quantityAfterChange;

    @ApiModelProperty(value = "补货单号(除了总仓入库之外,都要有)")
    private String replenishmentOrderNo;

    @ApiModelProperty(value = "变动时间")
    private Date createTime;

    @ApiModelProperty(value = "变动类型(OUTOFSTOCK->出库,WAREHOUSING->入库)")
    private String changeType;

    @ApiModelProperty(value = "变动方配送中心ID")
    private Long changeWmsMemberId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPmsSkuStockId() {
        return pmsSkuStockId;
    }

    public void setPmsSkuStockId(Long pmsSkuStockId) {
        this.pmsSkuStockId = pmsSkuStockId;
    }

    public Integer getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(Integer changeNum) {
        this.changeNum = changeNum;
    }

    public Integer getQuantityBeforeChange() {
        return quantityBeforeChange;
    }

    public void setQuantityBeforeChange(Integer quantityBeforeChange) {
        this.quantityBeforeChange = quantityBeforeChange;
    }

    public Integer getQuantityAfterChange() {
        return quantityAfterChange;
    }

    public void setQuantityAfterChange(Integer quantityAfterChange) {
        this.quantityAfterChange = quantityAfterChange;
    }

    public String getReplenishmentOrderNo() {
        return replenishmentOrderNo;
    }

    public void setReplenishmentOrderNo(String replenishmentOrderNo) {
        this.replenishmentOrderNo = replenishmentOrderNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Long getChangeWmsMemberId() {
        return changeWmsMemberId;
    }

    public void setChangeWmsMemberId(Long changeWmsMemberId) {
        this.changeWmsMemberId = changeWmsMemberId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pmsSkuStockId=").append(pmsSkuStockId);
        sb.append(", changeNum=").append(changeNum);
        sb.append(", quantityBeforeChange=").append(quantityBeforeChange);
        sb.append(", quantityAfterChange=").append(quantityAfterChange);
        sb.append(", replenishmentOrderNo=").append(replenishmentOrderNo);
        sb.append(", createTime=").append(createTime);
        sb.append(", changeType=").append(changeType);
        sb.append(", changeWmsMemberId=").append(changeWmsMemberId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}