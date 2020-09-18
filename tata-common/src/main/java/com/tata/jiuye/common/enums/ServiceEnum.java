package com.tata.jiuye.common.enums;

public enum ServiceEnum {

    GATEWAY("service.business.gate_way","网关"),
    DAIFU("service.business.payment","代付"),
    WECHAT("service.business.wechat","微信扫码"),
    ALIPAY("service.business.alipay","支付宝扫码"),
    ALIPAY_H5("service.business.alipay_h5","支付宝H5扫码"),
    QQPAY("service.business.qq_pay","QQ扫码"),
    WECHAT_H5("service.business.wechat_h5","微信H5"),
    WECHAT_JSAPI("service.business.wechat_jsapi","微信JS支付"),
    WECHAT_APP("service.business.wechat_app","微信APP"),
    WECHAT_JD("service.business.jd","京东扫码"),
    WECHAT_BAIDU("service.business.baidu","百度扫码"),
    WECHAT_UNION("service.business.union","银联扫码");

    private String value;
    private String text;

    private ServiceEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public Object getValue() {
        return this.value;
    }

    public String getText()
    {
        return this.text;
    }
}
