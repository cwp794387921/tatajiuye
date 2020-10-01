package com.tata.jiuye.portal.util;

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
        request.putQueryParameter("TemplateCode", "SMS_204126033");
        request.putQueryParameter("TemplateParam", "{\"nickName\":\"Tom\", \"custmer\":\"123\", \"productName\":\"内裤\", \"productNum\":\"3\", \"receiver\":\"厦门\", \"address\":\"福建\"}");

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
     *  发送补货通知
     */
    public String sendSms1(String phone, String code) {

        String result = "";
        DefaultProfile profile = DefaultProfile.getProfile(REGIONID, ACCESSKEYID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", REGIONID);
        request.putQueryParameter("PhoneNumbers", "13850075431");
        request.putQueryParameter("SignName", SIGNNAME);
        request.putQueryParameter("TemplateCode", "SMS_204126033");
        request.putQueryParameter("TemplateParam", "{\"nickName\":\"Tom\", \"custmer\":\"123\", \"productName\":\"内裤\", \"productNum\":\"3\", \"receiver\":\"厦门\", \"address\":\"福建\"}");



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
}
