package com.tata.jiuye.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class InventoryChangeRecord implements Serializable {
    @ApiModelProperty(value = "库存变动记录表ID")
    private Long id;

    @ApiModelProperty(value = "库存变动类型")
    private String changeOfType;

    @ApiModelProperty(value = "变动数量")
    private Integer changeNumber;

    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "变动时间")
    private Date createTime;

    @ApiModelProperty(value = "变动前数量")
    private Integer quantityBeforeChange;

    @ApiModelProperty(value = "变动后数量")
    private Integer quantityAfterChange;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangeOfType() {
        return changeOfType;
    }

    public void setChangeOfType(String changeOfType) {
        this.changeOfType = changeOfType;
    }

    public Integer getChangeNumber() {
        return changeNumber;
    }

    public void setChangeNumber(Integer changeNumber) {
        this.changeNumber = changeNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", changeOfType=").append(changeOfType);
        sb.append(", changeNumber=").append(changeNumber);
        sb.append(", createTime=").append(createTime);
        sb.append(", quantityBeforeChange=").append(quantityBeforeChange);
        sb.append(", quantityAfterChange=").append(quantityAfterChange);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
