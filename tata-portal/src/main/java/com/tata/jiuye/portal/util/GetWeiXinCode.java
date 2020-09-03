package com.tata.jiuye.portal.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class GetWeiXinCode {
    private final static String WECHAT_SESSION_HOST = "https://api.weixin.qq.com/sns/jscode2session";
    //小程序APPID
    private final static String WECHAT_APP_ID = "wxdaa9fb4025511958";
    //小程序秘钥
    private final static String WECHAT_SECRET = "56bcec96c36b270da318979e4c41f8fc";
    private static RedisService redisUtils;
    public static final String tokenKey = "tata_access_token";


    private  final static String appid = "wxdaa9fb4025511958";

    private  final static String appsecret="56bcec96c36b270da318979e4c41f8fc";



    @Autowired
    public void setRedisUtils(RedisService redisUtils) {
        this.redisUtils = redisUtils;
    }

    public static String getAccessTokenUrl(String appid, String secret) {

        String result = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
        return result;

    }

    public static String getTicketUrl(String accessToken){

        String ticket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";

        return ticket;

    }

    public static String getInfoUrl(String accessToken, String openid) {

        String result = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid;
        return result;

    }
    public static String getCodeRequestByBase(String appid, String url) {

        String result = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + url + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        return result;

    }

    public static String getCurrentOpenId(String code, String appid, String SECRET) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + SECRET + "&code=" + code + "&grant_type=authorization_code";
        return url;

    }




    /**
      * 开放平台获取token url
      */
    public static String getComponentTokenUrl(){
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
        return url;
    }
    /**
      * 开放平台获取preAuthCode url
      */
    public static String getPreAuthCodeUrl(String componentToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=" + componentToken;
        return url;
    }
    /**
      * 开放平台获取API调用令牌
      */
    public static String getAuthorizerAccessTokenUrl(String component_access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token="+component_access_token;
        return url;
    }



    public static JSONObject getInfoUrlByAccessToken(String accessToken, String openid){

        String getInfoUrl = GetWeiXinCode.getInfoUrl(accessToken,openid);
        String result = SendPushPost.sendGet(getInfoUrl);
        JSONObject httpRequest = JSONObject.parseObject(result);
        return httpRequest;
    }

    public static String getAccessToken(String appid, String secret,String key){
        /*String accessToken = redisUtils.get(key);
        if ((StringUtils.isEmpty(accessToken)) || ("NULL".equals(accessToken.toUpperCase()))) {*/
            String accessTokenUrl = GetWeiXinCode.getAccessTokenUrl(appid,secret);
            String result = SendPushPost.sendGet(accessTokenUrl);
            JSONObject httpRequest = JSONObject.parseObject(result);
            String accessToken = (String) httpRequest.get("access_token");
            Integer expiresIn = (Integer) httpRequest.get("expires_in");
            redisUtils.set(key,accessToken,expiresIn);
            return accessToken;
        /*}
        return accessToken;*/
    }



    /*public static JSONObject getOpenId(String code){

        String currentOpenIdurl = GetWeiXinCode.getCurrentOpenId
                (code, appid, appsecret);
        String result = SendPushPost.sendGet(currentOpenIdurl);
        JSONObject obj = JSONObject.parseObject(result);
        System.out.println(obj);
        if(obj.get("errcode").toString().equals("41002")){
            return null;
        }
        String accessToken = obj.get("access_token").toString();
        Integer expiresIn = (Integer) obj.get("expires_in");
        redisUtils.set(tokenKey, accessToken, expiresIn);
        //String openId = (String) obj.get("openid");
        return obj;
    }*/

    public static JSONObject getOpenId(String code){
        String result = HttpRequest.sendGet(WECHAT_SESSION_HOST,
                "appid=" + WECHAT_APP_ID +
                        "&secret="+ WECHAT_SECRET +
                        "&js_code="+ code + //前端传来的code
                        "&grant_type=authorization_code");
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.containsKey("errcode")) {
            Asserts.fail("code无效");
        }
        if(jsonObject.get("openid")==null){
            return null;
        }
        return jsonObject;
    }

    public static JSONObject getTicket(String accessToken) throws Exception{
        JSONObject httpRequest;
        String ticket = redisUtils.get("ticket").toString();
        if ((StrUtil.isEmpty(ticket)) || ("NULL".equals(ticket.toUpperCase()))) {
            String ticketUrl = GetWeiXinCode.getTicketUrl(accessToken);
            //String result = SendPushPost.sendGet(ticketUrl);
            String result = SendPushPost.sendPost("https://api.weixin.qq.com/cgi-bin/ticket/getticket","access_token="+accessToken+"&type=jsapi");
            httpRequest = JSONObject.parseObject(result);
            if(httpRequest.getInteger("errcode")==40001){
                return getTicket(getAccessToken(appid,appsecret, GetWeiXinCode.tokenKey));
            }
            Integer expiresIn = (Integer) httpRequest.get("expires_in");
            redisUtils.set("ticket", httpRequest,expiresIn);
            return httpRequest;
        }
        return JSONObject.parseObject(ticket);
    }

    public static JSONObject getAuthorizerAccessToken(String component_appid,String authorization_code,String component_access_token,String appId){
        JSONObject httpRequest;
        String auth = redisUtils.get(appId+"_authorization_info").toString();
        if ((StringUtils.isEmpty(auth)) || ("NULL".equals(auth.toUpperCase()))) {
            JSONObject param = new JSONObject();
            param.put("component_appid",component_appid);
            param.put("authorization_code",authorization_code);
            String authAccessTokenUrl = GetWeiXinCode.getAuthorizerAccessTokenUrl(component_access_token);
            String result = SendPushPost.sendPost(authAccessTokenUrl,param.toJSONString());
            httpRequest = JSONObject.parseObject(result);
            JSONObject authorization_info =JSONObject.parseObject(JSONObject.toJSONString(httpRequest.get("authorization_info")));
            Integer expires_in = (Integer) authorization_info.get("expires_in");
            String authorizer_access_token = (String) authorization_info.get("authorizer_access_token");
            String authorizer_refresh_token = (String) authorization_info.get("authorizer_refresh_token");
            redisUtils.set(appId+"_authorization_info", authorization_info,expires_in);
            //redisUtils.set(appId+"_authorizer_refresh_token", authorizer_refresh_token,-1);
            return authorization_info;
        }
        return JSONObject.parseObject(auth);
    }

}
