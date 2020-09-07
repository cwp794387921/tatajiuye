package com.tata.jiuye.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 获取直系客户业绩
 * @author
 */
@Data
public class TotalPerformanceResult {

    @ApiModelProperty("团队销售总数量")
    private Integer totalOrderCount;

    @ApiModelProperty("团队销售总额")
    private BigDecimal totalPayAmount;
}
