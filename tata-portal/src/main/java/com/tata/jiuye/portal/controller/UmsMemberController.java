package com.tata.jiuye.portal.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.DTO.RegisteredMemberParam;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.common.service.RedisService;
import com.tata.jiuye.model.AcctInfo;
import com.tata.jiuye.model.AcctSettleInfo;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.portal.service.AcctInfoService;
import com.tata.jiuye.portal.service.AcctSettleInfoService;
import com.tata.jiuye.portal.service.UmsMemberCacheService;
import com.tata.jiuye.portal.service.UmsMemberService;
import com.tata.jiuye.portal.util.AliyunSmsUtil;
import com.tata.jiuye.portal.util.HttpRequest;
import com.tata.jiuye.portal.util.ValidateCode;
import com.tata.jiuye.portal.util.inviteQrCode.InviteQrCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员登录注册管理Controller
 * Created by macro on 2018/8/3.
 */
@Controller
@Api(tags = "UmsMemberController", description = "会员登录注册管理")
@RequestMapping("/sso")
@RequiredArgsConstructor
public class UmsMemberController {

    @Value("${auth.wechat.sessionHost}")
    private String WECHAT_SESSION_HOST;
    //小程序APPID
    @Value("${auth.wechat.appId}")
    private String WECHAT_APP_ID;
    //小程序秘钥
    @Value("${auth.wechat.secret}")
    private String WECHAT_SECRET;

    private static final Logger log = LoggerFactory.getLogger(UmsMemberController.class);

    protected HttpServletRequest request;

    @Resource
    private  UmsMemberService memberService;

    @Resource
    private AliyunSmsUtil aliyunSmsUtil;

    @Resource
    private  RedisService redisService;

    @Resource
    private UmsMemberCacheService umsMemberCacheService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation("会员注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult register(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String telephone,
                                 @RequestParam String authCode) {
        memberService.register(username, password, telephone, authCode);
        return CommonResult.success(null,"注册成功");
    }

    @ApiOperation("会员登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam String username,
                              @RequestParam String password) {
        String token = memberService.login(username, password);
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取会员信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult info(Principal principal) {
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        UmsMember member = memberService.getCurrentMember();
        return CommonResult.success(member);
    }

    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAuthCode(@RequestParam String telephone) {
        String authCode = memberService.generateAuthCode(telephone);
        return CommonResult.success(authCode,"获取验证码成功");
    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePassword(@RequestParam String telephone,
                                 @RequestParam String password,
                                 @RequestParam String authCode) {
        memberService.updatePassword(telephone,password,authCode);
        return CommonResult.success(null,"密码修改成功");
    }


    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = memberService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("微信小程序登陆")
    @RequestMapping(value = "/WxApplogin", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult WxApplogin(@RequestParam String wxCode) {
        if (StrUtil.isEmpty(wxCode)) {
            return CommonResult.validateFailed("参数缺失");//404
        }
        String token = memberService.Wxlogin(wxCode);
        if (token == null) {
            return CommonResult.failed("登陆或注册失败,请联系管理员");  //500
        }
        if (token.equals("1")){
            return CommonResult.CodeAndMessage(400,"请验证手机号");   //400
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("微信小程序注册")
    @RequestMapping(value = "/registeredMember", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult registeredMember(@RequestBody RegisteredMemberParam registeredMemberParam){
        String phone = registeredMemberParam.getPhone();
        String verificationCode = registeredMemberParam.getVerificationCode();
        if(!StrUtil.isEmpty(phone)&&!StrUtil.isEmpty(verificationCode))
        {
            log.info("==》开始校验短信验证码，phone["+phone+"],code["+verificationCode+"]");
            //从缓存中取出验证码
            if(redisService.get("SMS_"+phone)==null){
                return CommonResult.validateFailed("验证码已过期");  //404
            }
            String valiCode=redisService.get("SMS_"+phone).toString();
            log.info("==》验证码["+valiCode+"]");
            if(!valiCode.equals(verificationCode)){
                return CommonResult.validateFailed("验证码错误");  //404
            }
            //验证通过，删除验证码
            redisService.del("SMS_"+phone);
        }
        String token = memberService.registeredMember(registeredMemberParam);
        return CommonResult.success(token);
    }


    /**
     * 响应验证码页面
     * @return
     */
    @ApiOperation("图文验证码")
    @RequestMapping(value="/validateCode", method = RequestMethod.POST)
    public String validateCode(String phone,Model model, HttpServletResponse response) throws Exception{
        // 设置响应的类型格式为图片格式
        if(StrUtil.isEmpty(phone)){
            model.addAttribute("msg","参数错误");
            return "message";
        }
        /** 将验证码放入缓存  **/
        ValidateCode vCode = new ValidateCode(120,40);
        redisService.set(phone,vCode.getCode(),5*60);//五分钟
        response.setContentType("image/jpeg"); //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        log.info("图形验证码="+vCode.getCode());
        vCode.write(response.getOutputStream());
        return null;
    }


    @ApiOperation("获取短信验证码")
    @RequestMapping(value = "/smsAPI", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult smsAPI(@RequestParam String phone) {
        log.info("===>接收到发送短信验证码请求：手机号["+phone+"]");
        //生成4位随机数
        int code=(int)((Math.random()*9+1)*1000);
        String result=aliyunSmsUtil.sendSms(phone,String.valueOf(code));
        if(result==null){
            return CommonResult.failed("短信发送失败");
        }else {
            JSONObject resultJson=JSONObject.parseObject(result);
            String resultCode=resultJson.get("Code").toString();
            if(!resultCode.equals("OK")){
                return CommonResult.failed(resultJson.get("Message").toString());
            }
        }
        //存入redis
        redisService.set("SMS_"+phone,String.valueOf(code),5*60);
        log.info("验证码为 "+code);
        return CommonResult.success("短信发送成功");
    }

    @ApiOperation("测试通过code获取openId与sessionkey")
    @RequestMapping(value = "/testGetOpenId", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getOpenIdAndSessionKey(@RequestParam @ApiParam("微信的CODE") String code){
        String result = HttpRequest.sendGet(WECHAT_SESSION_HOST,
                "appid=" + WECHAT_APP_ID +
                        "&secret="+ WECHAT_SECRET +
                        "&js_code="+ code + //前端传来的code
                        "&grant_type=authorization_code");
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.containsKey("errcode")) {
            Asserts.fail("code无效");
        }
        String jsonObjectStr = jsonObject.toJSONString();
        String openId = jsonObject.get("openid").toString();
        if (StringUtils.isEmpty(openId)) {
            Asserts.fail("openid为空");
        }
        return CommonResult.success(openId);
    }



    @ApiOperation("获取分享图片(拼接二维码)")
    @RequestMapping(value = "/getShareQrCodeBase64",method = RequestMethod.POST)
    @ResponseBody
    //public CommonResult getShareQrCodeBase64(@RequestParam(required = false) @ApiParam("海报URL")String imgUrl) throws Exception{
    public CommonResult getShareQrCodeBase64() throws Exception{
        UmsMember currentMember = memberService.getCurrentMember();
        String invitationCode = currentMember.getInviteCode();
        //String str = "/pages/login/register/index?invitationCode="+invitationCode+"&isShare=1";
        //log.info("----------------str : "+str);
        String accesstoken = InviteQrCode.postToken();
        int size = 280;
        int x = 50;
        int y = 160;
        String imgUrl = "http://cscyimages.oss-cn-zhangjiakou.aliyuncs.com/jiuye/images/20200907/47e1e3d629e12a049d0154d23c0e806.jpg";
        String resultUrl =  InviteQrCode.mergeImageAndQRToFileUrl(imgUrl,invitationCode,size,x,y,accesstoken);
        return CommonResult.success(resultUrl);
    }

    @ApiOperation("删除用户缓存")
    @RequestMapping(value = "/delCash",method = RequestMethod.POST)
    @ResponseBody
    public void delCash(@RequestParam @ApiParam("要删除缓存的用户ID") Long memberId){
        umsMemberCacheService.delMember(memberId);
    }
}
