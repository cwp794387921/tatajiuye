package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class UmsMemberInviteRelation implements Serializable {
    private Long id;

    @ApiModelProperty(value = "直推人id")
    private Long fatherMemberId;

    @ApiModelProperty(value = "间推人id")
    private Long grandpaMemberId;

    @ApiModelProperty(value = "自身用户ID")
    private Long memberId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFatherMemberId() {
        return fatherMemberId;
    }

    public void setFatherMemberId(Long fatherMemberId) {
        this.fatherMemberId = fatherMemberId;
    }

    public Long getGrandpaMemberId() {
        return grandpaMemberId;
    }

    public void setGrandpaMemberId(Long grandpaMemberId) {
        this.grandpaMemberId = grandpaMemberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
        sb.append(", fatherMemberId=").append(fatherMemberId);
        sb.append(", grandpaMemberId=").append(grandpaMemberId);
        sb.append(", memberId=").append(memberId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}