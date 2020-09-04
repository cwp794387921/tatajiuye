package com.tata.jiuye.portal.DTO;

import com.tata.jiuye.model.AcctSettleInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BalanceFlowResult {

    @ApiModelProperty("总余额")
    private BigDecimal totalBalance;

    @ApiModelProperty("余额明细")
    private List<AcctSettleInfo> acctSettleInfos;
}
