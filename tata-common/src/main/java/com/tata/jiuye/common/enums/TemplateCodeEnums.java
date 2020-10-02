package com.tata.jiuye.common.enums;

public enum TemplateCodeEnums {
    JD("SMS_204111063","接单通知"),
    XD("SMS_204106064","下单通知"),
    BH("SMS_204115964","补货通知"),
    PS("SMS_204111046","下单通知配送"),
    SY("SMS_204126035","收益到账通知");

    private String value;
    private String text;

    private TemplateCodeEnums(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return this.value;
    }

    public String getText()
    {
        return this.text;
    }
}
