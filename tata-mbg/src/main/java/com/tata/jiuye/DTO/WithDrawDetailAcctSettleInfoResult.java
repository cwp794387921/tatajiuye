package com.tata.jiuye.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WithDrawDetailAcctSettleInfoResult {

    @ApiModelProperty(value = "流水号")
    private Long flowNo;

    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawlAmount;

    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "提现时间")
    private Date withdrawlDate;

    @ApiModelProperty(value = "状态")
    private String flowStatus;

    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "打款时间")
    private Date makeMoneyDate;
}
