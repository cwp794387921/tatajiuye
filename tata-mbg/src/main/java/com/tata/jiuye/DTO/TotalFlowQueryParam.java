package com.tata.jiuye.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TotalFlowQueryParam {

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("查询开始时间")
    private Date startDate;

    @ApiModelProperty("查询结束时间")
    private Date endDate;

    @ApiModelProperty("流水类型")
    private String flowType;
}
