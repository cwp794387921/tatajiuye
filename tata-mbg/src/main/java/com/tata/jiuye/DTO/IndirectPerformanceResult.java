package com.tata.jiuye.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 获取间接邀请客户业绩
 * @author
 */
@Data
public class IndirectPerformanceResult implements Comparable<IndirectPerformanceResult>{

    private Long id;

    @ApiModelProperty("间接邀请用户头像")
    private String icon;

    @ApiModelProperty("直系用户头像")
    private String fatherIcon;

    @ApiModelProperty("直系客户昵称")
    private String fatherNickname;

    @ApiModelProperty("间接邀请客户昵称")
    private String nickname;

    @ApiModelProperty("下单数量")
    private Integer orderCount;

    @ApiModelProperty("金额")
    private BigDecimal totalPayAmount;



    @Override
    public int compareTo(IndirectPerformanceResult o) {
        return o.getOrderCount()-this.getOrderCount()  ;
    }
}
