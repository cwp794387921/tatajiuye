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
            String code="123456";
            DefaultProfile profile =
                    DefaultProfile.getProfile("cn-hangzhou", "", "");
            IAcsClient client = new DefaultAcsClient(profile);
            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", "17689218784");
            request.putQueryParameter("SignName", "");
            request.putQueryParameter("TemplateCode", "SMS_196642878");
            request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
            try {
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }

        public String sendSms(String phone,String code){
            String result="";
            DefaultProfile profile =
                    DefaultProfile.getProfile(REGIONID, ACCESSKEYID, SECRET);
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
            request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
            try {
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());
                result=response.getData();
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.getMessage();
            }
            return result;
        }


}
