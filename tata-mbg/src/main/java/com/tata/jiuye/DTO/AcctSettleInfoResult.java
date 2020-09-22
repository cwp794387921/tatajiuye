package com.tata.jiuye.DTO;

import com.tata.jiuye.model.AcctSettleInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AcctSettleInfoResult extends AcctSettleInfo {

    @ApiModelProperty(value = "用户头像")
    private String icon;
}
