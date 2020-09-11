package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ReplenishableParams {

    @Getter
    @Setter
    @ApiModelProperty("补货单id")
    private Long id;
    @Getter
    @Setter
    @ApiModelProperty("图片")
    private List<String> imgs;
}
