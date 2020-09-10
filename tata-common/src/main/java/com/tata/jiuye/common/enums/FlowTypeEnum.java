package com.tata.jiuye.common.enums;

public enum FlowTypeEnum {

    COMMISSION_INCOME("commission_income","分佣收入"),
    DELIVERY_FEE("delivery_fee","配送费"),
    STORAGE_ALLOWANCE("storage _allowance","仓储补助"),
    WITHDRWA("withdraw","提现"),
    INCOME("income","收入"),
    EXPENDITURE("expenditure","支出");

    public String value;
    public String  explain;

    private FlowTypeEnum(String value, String explain){
        this.value = value;
        this.explain =explain;
    }

    @Override
    public String toString() {
        return String.valueOf ( this.value);
    }

}
