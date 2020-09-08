package com.tata.jiuye.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 获取提现申请列表参数
 */
@Data
public class WithdrawExamineQueryParam {

    @ApiModelProperty("申请人ID")
    private Long applicantMemberId;

    @ApiModelProperty("申请人名称")
    private String applicantMemberName;

    @ApiModelProperty("审批人ID")
    private Long approverMemberId;

    @ApiModelProperty("审批人名称")
    private String approverMemberName;

    @ApiModelProperty("提现金额")
    private BigDecimal withdrawalAmount;

    @ApiModelProperty("申请状态")
    private Integer status;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页条数")
    private Integer pageSize;
}
