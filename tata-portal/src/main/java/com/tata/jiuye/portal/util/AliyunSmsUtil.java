package com.tata.jiuye.portal.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AliyunSmsUtil {

    @Value("${aliyun.sms.regionid}")
    private String REGIONID;
    @Value("${aliyun.sms.accesskeyid}")
    private String ACCESSKEYID;
    @Value("${aliyun.sms.secret}")
    private String SECRET;
    @Value("${aliyun.sms.signname}")
    private String SIGNNAME;
    @Value("${aliyun.sms.templatecode}")
    private String TEMPLATECODE;

    public static void main(String[] args) {
        String nickName = "大爷";
        String custmer = "小爷";

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String strDate2 = dtf2.format(time);

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FyMjpYjQTaWUqe1gq8p", "i3tLJpZ4nQlgakk24fxAtUSyF4ntKK");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();

        String result = "";
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "hangzhou");
        request.putQueryParameter("PhoneNumbers", "13850075431");
        request.putQueryParameter("SignName", "山图世纪合一");
        request.putQueryParameter("TemplateCode", "SMS_204115968");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickName", " [" + nickName + "]");
        jsonObject.put("custmer", " [" + custmer + "]");
        jsonObject.put("time", strDate2);

        request.putQueryParameter("TemplateParam", jsonObject.toJSONString());

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            result = response.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendSms(String phone, String code) {
        String result = "";
        DefaultProfile profile = DefaultProfile.getProfile(REGIONID, ACCESSKEYID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", REGIONID);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SIGNNAME);
        request.putQueryParameter("TemplateCode", TEMPLATECODE);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            result = response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.getMessage();
        }
        return result;
    }

    /**
     * 发送补货通知
     *
     * @param phone       手机号码
     * @param nickName    上级昵称
     * @param custmer     下级昵称
     * @param productName 商品名称
     * @param productNum  数量
     * @param receiver    收货人
     * @param address     地址
     * @return
     */
    public String sendSms1(String phone, String nickName, String custmer, String productName, String productNum, String receiver, String address) {

        String result = "";
        DefaultProfile profile = DefaultProfile.getProfile(REGIONID, ACCESSKEYID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", REGIONID);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "山图世纪合一");
        request.putQueryParameter("TemplateCode", "SMS_204115964");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickName", nickName);
        jsonObject.put("custmer", custmer);
        jsonObject.put("productName", productName);
        jsonObject.put("productNum", productNum);
        jsonObject.put("receiver", receiver);
        jsonObject.put("address", address);

        request.putQueryParameter("TemplateParam", jsonObject.toJSONString());

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            result = response.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 下单通知配送中心
     *
     * @param phone       手机号
     * @param nickName    配送中心昵称
     * @param custmer     下单会员昵称
     * @param productName 商品名称
     * @param productNum  商品数量
     * @param receiver    收货人
     * @param address     地址
     * @param time        下单时间
     * @return
     */
    public String sendSms2(String phone, String nickName, String custmer, String productName, String productNum, String receiver, String address, String time) {

        String result = "";
        DefaultProfile profile = DefaultProfile.getProfile(REGIONID, ACCESSKEYID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", REGIONID);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "山图世纪合一");
        request.putQueryParameter("TemplateCode", "SMS_204111046");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickName", nickName);
        jsonObject.put("custmer", custmer);
        jsonObject.put("productName", productName);
        jsonObject.put("productNum", productNum);
        jsonObject.put("receiver", receiver);
        jsonObject.put("address", address);
        jsonObject.put("time", time);

        request.putQueryParameter("TemplateParam", jsonObject.toJSONString());

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            result = response.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 分佣，配送费，仓补收入到账通知
     *
     * @param phone    手机号
     * @param nickName 配送中心昵称
     * @param orderNo  下单会员昵称
     * @param ammout   商品名称
     * @return
     */
    public String sendSms3(String phone, String nickName, String orderNo, String ammout) {

        String result = "";
        DefaultProfile profile = DefaultProfile.getProfile(REGIONID, ACCESSKEYID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", REGIONID);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "山图世纪合一");
        request.putQueryParameter("TemplateCode", "SMS_204126035");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickName", nickName);
        jsonObject.put("orderNo", orderNo);
        jsonObject.put("ammout", ammout);

        request.putQueryParameter("TemplateParam", jsonObject.toJSONString());

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            result = response.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 下级注册成功 通知上级
     *
     * @param phone    手机号
     * @param nickName 配送中心昵称
     * @param custmer  下单会员昵称
     * @param time     商品名称
     * @return
     */
    public String sendSms4(String phone, String nickName, String custmer, String time) {
        String result = "";
        DefaultProfile profile = DefaultProfile.getProfile(REGIONID, ACCESSKEYID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", REGIONID);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "山图世纪合一");
        request.putQueryParameter("TemplateCode", "SMS_204115968");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickName", " [" + nickName + "]");
        jsonObject.put("custmer", " [" + custmer + "]");
        jsonObject.put("time", time);

        request.putQueryParameter("TemplateParam", jsonObject.toJSONString());

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            result = response.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
