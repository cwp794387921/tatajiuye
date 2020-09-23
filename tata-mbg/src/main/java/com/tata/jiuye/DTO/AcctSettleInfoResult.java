package com.tata.jiuye.DTO;

import com.tata.jiuye.model.AcctSettleInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AcctSettleInfoResult extends AcctSettleInfo {

    @ApiModelProperty(value = "来源用户头像")
    private String icon;

    @ApiModelProperty(value = "来源用户名")
    private String nickname;

    @ApiModelProperty(value = "流水描述")
    private String flowDesc;
}
