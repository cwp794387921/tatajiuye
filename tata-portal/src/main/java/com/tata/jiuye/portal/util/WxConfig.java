package com.tata.jiuye.portal.util;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

public class WxConfig implements WXPayConfig {

    public static String PATH="/usr/local/tata-jiuye/tata-portal/apiclient_cert.p12";
    //public static String PATH = "/usr/local/tata-jiuye/tata-portal/prod/apiclient_cert.p12";


    @Value("${auth.wechat.appId}")
    private String APPID;
    @Value("${auth.wechat.pay.mchId}")
    private String MCHID;
    @Value("${auth.wechat.pay.notifyurl}")
    private String NOTIFYURL;

    private byte[] certData;

    public WxConfig() throws Exception {
        /*URL resource = this.getClass().getClassLoader().getResource("apiclient_cert.p12");
        String path = resource.getPath();
        File file = new File(path);*/
        File file = new File(PATH);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }
    @Override
    public String  getAppID() {
        return "wxdaa9fb4025511958";
        //return "wxd920cb3c36501e46";
    }

    //商户号
    @Override
    public String getMchID() {
        return "1494508502";
        //return "1601954250";
    }

    public String getAppSecret() {
        return "56bcec96c36b270da318979e4c41f8fc";
        //return "5c4953f10adadc5cf6bcee10b27c108c";
    }
    @Override
    public String getKey() {
        return "xiamenshihuliquceyiwangluo111111";
        //return "HYKJxmjdiosajdiosjdoidj545454545";
    }

    public String getNotifyUrl(){
        return "https://www.cyjyt.com:8085/pay/wxNotify";
        //return "https://www.xmzzhy.com:8085/pay/wxNotify";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    /**
     * 微信支付接口地址
     */
    //微信支付统一接口(POST)
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信退款接口(POST)
    public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //订单查询接口(POST)
    public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    //关闭订单接口(POST)
    public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
    //退款查询接口(POST)
    public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
    //对账单接口(POST)
    public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    //短链接转换接口(POST)
    public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
    //接口调用上报接口(POST)
    public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
    //微信公众号支付获取openId
    public final static String oauth_url = "https://api.weixin.qq.com/sns/oauth/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
}
