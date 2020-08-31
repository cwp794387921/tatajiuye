package com.tata.jiuye.portal.controller;

import cn.hutool.core.util.StrUtil;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.common.service.RedisService;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.portal.service.UmsMemberService;
import com.tata.jiuye.portal.service.impl.UmsMemberServiceImpl;
import com.tata.jiuye.portal.util.ValidateCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
@Slf4j
public class UmsMemberController {

    private static final Logger log = LoggerFactory.getLogger(UmsMemberController.class);

    protected HttpServletRequest request;

    @Resource
    private  UmsMemberService memberService;

    @Resource
    private  RedisService redisService;

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
    public CommonResult WxApplogin(@RequestParam String wxCode,@RequestParam String phone,@RequestParam(value = "fatherId", required=false) String fatherId) {
        if (StrUtil.isEmpty(wxCode)||StrUtil.isEmpty(phone)) {
            return CommonResult.validateFailed("参数缺失");
        }
        String token = memberService.Wxlogin(wxCode,phone,fatherId);
        if (token == null) {
            return CommonResult.failed("登陆或注册失败,请联系管理员");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    /**
     * 响应验证码页面
     * @return
     */
    @ApiOperation("图文验证码")
    @RequestMapping(value="/validateCode")
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
        HttpSession session = request.getSession();
        log.info("图形验证码="+vCode.getCode());
        session.setAttribute("verifyCode", vCode.getCode());
        vCode.write(response.getOutputStream());
        return null;
    }


    @ApiOperation("获取短信验证码")
    @RequestMapping(value = "/smsAPI", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult smsAPI(@RequestParam String phone) {


        return CommonResult.success("短信发送成功");
    }


}
