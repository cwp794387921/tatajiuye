package com.tata.jiuye.common.config;

public class PayConfig {
    //请求方式
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
//    交易商户号
    public static final String MERCHANT_NO = "1";
//    交易商户私钥
    public static final String PRIVATE_KEY = "";
//    渠道公钥
    public static final String PUBLIC_KEY = "";
//    交易请求地址
    public static final String PAY_URL = "http://pts.rtw.yum-pay.com/v1/yumpay/trade/tradePayment";

    public static final String NOTIFY_URL = "https://www.cyjyt.com:8085/pay/wxNotify";

}
