package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class UmsMemberAndMember implements Serializable {
    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "直推人id")
    private String fatherId;

    @ApiModelProperty(value = "间推人id")
    private String grandpaId;

    @ApiModelProperty(value = "推荐人id")
    private String recommendId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getGrandpaId() {
        return grandpaId;
    }

    public void setGrandpaId(String grandpaId) {
        this.grandpaId = grandpaId;
    }

    public String getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(String recommendId) {
        this.recommendId = recommendId;
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
        sb.append(", userName=").append(userName);
        sb.append(", fatherId=").append(fatherId);
        sb.append(", grandpaId=").append(grandpaId);
        sb.append(", recommendId=").append(recommendId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}