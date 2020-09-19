package com.tata.jiuye.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WithdrawalExamine implements Serializable {
    private Long id;

    @ApiModelProperty(value = "申请人id")
    private Long applicantMemberId;

    @ApiModelProperty(value = "申请人姓名")
    private String applicantMemberName;

    @ApiModelProperty(value = "审批人id")
    private Long approverMemberId;

    @ApiModelProperty(value = "审批人姓名")
    private String approverMemberName;

    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "申请时间")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审批时间")
    private Date updateTime;

    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawalAmount;

    @ApiModelProperty(value = "状态 0待审核 1审核通过 2驳回")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remake;

    @ApiModelProperty(value = "交易流水ID（变动流水）")
    private Long acctSettleInfoId;

    @ApiModelProperty(value = "账户类型 0普通会员账户 1配送中心账户")
    private String acctType;

    private String name;
    private String type;
    private String bank;
    private String accountId;
    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicantMemberId() {
        return applicantMemberId;
    }

    public void setApplicantMemberId(Long applicantMemberId) {
        this.applicantMemberId = applicantMemberId;
    }

    public String getApplicantMemberName() {
        return applicantMemberName;
    }

    public void setApplicantMemberName(String applicantMemberName) {
        this.applicantMemberName = applicantMemberName;
    }

    public Long getApproverMemberId() {
        return approverMemberId;
    }

    public void setApproverMemberId(Long approverMemberId) {
        this.approverMemberId = approverMemberId;
    }

    public String getApproverMemberName() {
        return approverMemberName;
    }

    public void setApproverMemberName(String approverMemberName) {
        this.approverMemberName = approverMemberName;
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

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public Long getAcctSettleInfoId() {
        return acctSettleInfoId;
    }

    public void setAcctSettleInfoId(Long acctSettleInfoId) {
        this.acctSettleInfoId = acctSettleInfoId;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applicantMemberId=").append(applicantMemberId);
        sb.append(", applicantMemberName=").append(applicantMemberName);
        sb.append(", approverMemberId=").append(approverMemberId);
        sb.append(", approverMemberName=").append(approverMemberName);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", withdrawalAmount=").append(withdrawalAmount);
        sb.append(", status=").append(status);
        sb.append(", remake=").append(remake);
        sb.append(", acctSettleInfoId=").append(acctSettleInfoId);
        sb.append(", acctType=").append(acctType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
