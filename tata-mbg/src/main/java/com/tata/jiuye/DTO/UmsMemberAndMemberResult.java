package com.tata.jiuye.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UmsMemberAndMemberResult {

    @ApiModelProperty("直邀人用户ID")
    private Long fatherId;

    @ApiModelProperty("间邀人用户ID")
    private Long grandpaId;
}
