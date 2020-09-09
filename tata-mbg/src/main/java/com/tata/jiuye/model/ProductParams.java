package com.tata.jiuye.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class ProductParams {
    @Getter
    @Setter
    @ApiModelProperty("商品id")
    private Long id;
    @Getter
    @Setter
    @ApiModelProperty("商品单价")
    private BigDecimal price;
    @Getter
    @Setter
    @ApiModelProperty("数量")
    private Integer number;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", price=").append(price);
        sb.append(", number=").append(number);
        sb.append("]");
        return sb.toString();
    }


}
