package com.tata.jiuye.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 获取直系客户业绩
 * @author
 */
@Data
public class DirectPerformanceResult {

    @ApiModelProperty("客户昵称")
    private String nickname;

    @ApiModelProperty("下单数量")
    private Integer orderCount;

    @ApiModelProperty("金额")
    private BigDecimal totalPayAmount;
}
