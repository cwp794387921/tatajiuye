package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AcctInfo implements Serializable {
    @ApiModelProperty(value = "账户id")
    private String acctId;

    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "所属会员手机号")
    private String memberNo;

    @ApiModelProperty(value = "所属机构id")
    private String branchNo;

    @ApiModelProperty(value = "生效日期")
    private Date effDate;

    @ApiModelProperty(value = "失效日期")
    private Date expirDate;

    @ApiModelProperty(value = "状态 00失效  01生效")
    private Integer status;

    @ApiModelProperty(value = "插入时间")
    private Date insertTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remake;

    private static final long serialVersionUID = 1L;

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public Date getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(Date expirDate) {
        this.expirDate = expirDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", acctId=").append(acctId);
        sb.append(", balance=").append(balance);
        sb.append(", memberNo=").append(memberNo);
        sb.append(", branchNo=").append(branchNo);
        sb.append(", effDate=").append(effDate);
        sb.append(", expirDate=").append(expirDate);
        sb.append(", status=").append(status);
        sb.append(", insertTime=").append(insertTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remake=").append(remake);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}