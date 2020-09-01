package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class UmsMemberAndBranch implements Serializable {
    @ApiModelProperty(value = "用户id(手机号)")
    private String userName;

    @ApiModelProperty(value = "是否是配送中心(00否  01是)")
    private Integer isBranch;

    @ApiModelProperty(value = "父级配送中心id")
    private String parent;

    @ApiModelProperty(value = "会员配送中心id")
    private String userBarnch;

    private static final long serialVersionUID = 1L;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIsBranch() {
        return isBranch;
    }

    public void setIsBranch(Integer isBranch) {
        this.isBranch = isBranch;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getUserBarnch() {
        return userBarnch;
    }

    public void setUserBarnch(String userBarnch) {
        this.userBarnch = userBarnch;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userName=").append(userName);
        sb.append(", isBranch=").append(isBranch);
        sb.append(", parent=").append(parent);
        sb.append(", userBarnch=").append(userBarnch);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}