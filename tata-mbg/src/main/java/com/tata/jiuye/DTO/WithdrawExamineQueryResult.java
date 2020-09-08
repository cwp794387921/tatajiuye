package com.tata.jiuye.DTO;

import com.tata.jiuye.model.WithdrawalExamine;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取提现申请列表参数
 */
@Data
public class WithdrawExamineQueryResult extends WithdrawalExamine {
    @ApiModelProperty("申请状态")
    private String statusName;
}
