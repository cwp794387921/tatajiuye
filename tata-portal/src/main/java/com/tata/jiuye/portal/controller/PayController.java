package com.tata.jiuye.portal.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.mapper.OmsOrderMapper;
import com.tata.jiuye.model.OmsOrder;
import com.tata.jiuye.portal.domain.OmsOrderDetail;
import com.tata.jiuye.portal.service.OmsPortalOrderService;
import com.tata.jiuye.portal.util.GetWeiXinCode;
import com.tata.jiuye.portal.util.MD5Util;
import com.tata.jiuye.portal.util.WxConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

@Api("支付接口")
@CrossOrigin()
@Controller
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PayController {
    public static final Logger log = LoggerFactory.getLogger(PayController.class);
    @Resource
    private OmsPortalOrderService portalOrderService;
    @Resource
    private OmsOrderMapper orderMapper;

    @ApiOperation("微信web支付接口")
    @PostMapping("/wxWebRechargePay")
    @ResponseBody
    public CommonResult wxWebRechargePay(@RequestParam @ApiParam("订单编号")String orderNum, @RequestParam @ApiParam("微信code")String code, HttpServletRequest httpRequest,
                                         HttpServletResponse httpResponse)throws ServletException, IOException {
        log.info("===========微信web支付:进入============");
        log.info("===========用户请求IP:{}============",getIp(httpRequest));
        if(StrUtil.isEmpty(orderNum)||StrUtil.isEmpty(code)){
            return CommonResult.validateFailed("参数错误");
        }
        JSONObject result = GetWeiXinCode.getOpenId(code);
        String openId = result.get("openid").toString();
        if (openId==null){
            return CommonResult.failed("获取openid失败");
        }
        OmsOrder omsOrder = orderMapper.selectByOrderNum(orderNum);
        JSONObject jsonObject=new JSONObject();
        if(omsOrder!=null) {
            BigDecimal money = null;

            if (omsOrder.getPayAmount() == null) {
                return CommonResult.failed("订单金额不存在");
            } else {
                money = omsOrder.getPayAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            omsOrder.setPaymentTime(new Date());
            orderMapper.updateByPrimaryKey(omsOrder);
            try{
                WxConfig wxConfig=new WxConfig();
                WXPay wxPay=new WXPay(wxConfig);
                Map<String,String> map=new HashMap<>();
                SortedMap<Object,Object> map1 = new TreeMap<Object,Object>();
                map.put("nonce_str", RandomStringUtils.randomAlphanumeric(32));
                map.put("appid", wxConfig.getAppID());
                map.put("mch_id", wxConfig.getMchID());
                map.put("out_trade_no",orderNum);
                map.put("total_fee",money.multiply(new BigDecimal(100)).intValue()+"");
                map.put("spbill_create_ip",getIp(httpRequest));
                map.put("notify_url",wxConfig.getNotifyUrl());
                map.put("trade_type","JSAPI");
                map.put("openid",openId);
                map.put("body", "升级套餐");
                for (String key : map.keySet()){
                    System.out.println("key: "+ key + "; value: " + map.get(key));
                    map1.put(key,map.get(key));
                }
                map.put("sign",createSign("UTF-8",map1));
                Map<String,String> res=wxPay.unifiedOrder(map);
                System.out.println(res);
                if(res.get("result_code").equals("FAIL")){
                    return CommonResult.failed(res.get("err_code_des"));
                }
                jsonObject.put("appId",res.get("appid"));
                Date date = new Date();
                long dateTime = date.getTime();
                jsonObject.put("timeStamp",dateTime/1000);
                jsonObject.put("nonceStr",RandomStringUtils.randomAlphanumeric(32));
                jsonObject.put("package","prepay_id="+res.get("prepay_id"));
                jsonObject.put("signType","MD5");
                SortedMap<Object,Object> map2 = new TreeMap<Object,Object>();
                map2.put("appId",jsonObject.get("appId"));
                map2.put("timeStamp",jsonObject.get("timeStamp"));
                map2.put("nonceStr",jsonObject.get("nonceStr"));
                map2.put("package",jsonObject.get("package"));
                map2.put("signType",jsonObject.get("signType"));
                jsonObject.put("paySign",createSign("UTF-8",map2));
            }catch (Exception e){
                System.out.println(e.getMessage());
                return CommonResult.failed(e.getMessage());
            }
        }else{
            return   CommonResult.failed("订单信息不存在");
        }
        return CommonResult.success(jsonObject);
    }

    @PostMapping("/wxNotify")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response){
        System.out.println("微信支付回调");
        try {
            String resXml = "";
            WxConfig wxConfig = new WxConfig();
            WXPay wxPay = new WXPay(wxConfig);

            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
            Map<String, String> map = WXPayUtil.xmlToMap(result);

            if (wxPay.isPayResultNotifySignatureValid(map)) {
                System.out.println("微信支付-签名验证成功");
                System.out.println(map.toString());
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                //业务处理开始
                String orderNum=map.get("out_trade_no");
                String wxOrderNum=map.get("transaction_id");
                OmsOrder omsOrder = orderMapper.selectByPrimaryKey(Long.parseLong(orderNum));
                if(omsOrder!=null&!omsOrder.getStatus().equals("0")){
                    //更新交易记录
                    omsOrder.setChannelOrderNum(wxOrderNum);//微信订单号
                    omsOrder.setModifyTime(new Date());
                    omsOrder.setStatus(1);
                    orderMapper.updateByPrimaryKey(omsOrder);
                }
                //业务处理结束
            }
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 微信支付签名算法sign
     * @param characterEncoding
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){

        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + "1111");
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }



    /**
     * 获取请求人的IP地址
     * @return
     */
    protected String getIp(HttpServletRequest httpRequest){
        String ip="";
        try {
            ip = httpRequest.getHeader("X-Real-IP");
            if (!StrUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
            ip = httpRequest.getHeader("X-Forwarded-For");
            if (!StrUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
                // 多次反向代理后会有多个IP值，第一个为真实IP。
                int index = ip.indexOf(',');
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            } else {
                ip=httpRequest.getRemoteAddr();
                if(StrUtil.isEmpty(ip)){
                    return "";
                }
                if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")){
                    return "106.122.213.38";
                }
                return httpRequest.getRemoteAddr();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

}