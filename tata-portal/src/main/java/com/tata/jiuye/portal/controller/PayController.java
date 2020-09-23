package com.tata.jiuye.portal.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.common.collect.Maps;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.common.enums.ServiceEnum;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.common.service.RedisService;
import com.tata.jiuye.common.utils.*;
import com.tata.jiuye.mapper.*;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.common.constant.StaticConstant;
import com.tata.jiuye.portal.service.*;
import com.tata.jiuye.portal.util.MD5Util;
import com.tata.jiuye.portal.util.WxConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
    @Resource
    private OmsOrderItemService omsOrderItemService;
    @Resource
    private  OmsOrderItemMapper omsOrderItemMapper;

    @Resource
    private UmsMemberMapper umsMemberMapper;
    @Resource
    private UmsMemberService umsMemberService;
    @Resource
    private WmsMemberMapper wmsMemberMapper;
    @Resource
    private OmsDistributionMapper distributionMapper;
    @Resource
    private OmsDistributionItemMapper distributionItemMapper;
    @Resource
    private AcctSettleInfoService acctSettleInfoService;
    @Resource
    private PmsProductMapper productMapper;
    @Resource
    private AcctInfoService acctInfoService;
    @Resource
    private RedisService redisService;

    @Value("${umsmemberlevelname.vip}")
    private String UMS_MEMBER_LEVEL_NAME_VIP;
    @Value("${auth.wechat.appId}")
    private String APPID;
    @Value("${auth.wechat.pay.mchId}")
    private String MCHID;
    @Value("${auth.wechat.pay.notifyurl}")
    private String NOTIFYURL;

    @ApiOperation("微信web支付接口")
    @PostMapping("/wxWebRechargePay")
    @ResponseBody
    public CommonResult wxWebRechargePay(@RequestParam @ApiParam("订单编号")String orderNum, HttpServletRequest httpRequest,
                                         HttpServletResponse httpResponse)throws ServletException, IOException {
        log.info("===========微信web支付:进入============");
        log.info("===========用户请求IP:{}============",getIp(httpRequest));
        log.info("APPID : "+APPID);
        log.info("MCHID : "+MCHID);
        log.info("NOTIFYURL : "+NOTIFYURL);
        if(StrUtil.isEmpty(orderNum)){
            return CommonResult.validateFailed("参数错误");
        }
        UmsMember member = umsMemberService.getCurrentMember();
        member=umsMemberMapper.selectByPrimaryKey(member.getId());
        String openId=member.getOpenid();
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
                Map<String,String> map = Maps.newHashMap();
                map.put("merchantNo", Config.MERCHANT_NO);
                map.put("orderAmount",money.toString());
                map.put("service", ServiceEnum.WECHAT_APPLET.getValue().toString());
                map.put("orderTitle","塔塔酒业");
                map.put("notifyUrl",Config.NotifyUrl);
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("userId",openId);
                jsonObject1.put("orderType","wechatJs");
                jsonObject1.put("subAppid",Config.MERCHANT_NO);
                map.put("attach",jsonObject1.toJSONString());
                map.put("merchantOrderNo",orderNum);
                TreeMap<String, Object> sortedMap = new TreeMap<String, Object>(map);
                String sign = EncryUtil.handleRSA(sortedMap, Config.PRIVATE_KEY);
                map.put("sign", Base64Util.encodeByBase64(sign));
                String content = ChannelUtils.getContent(map);
                log.info("请求参数："+content);
                String responseStr = HttpRequestUtils.readContentFromPost(Config.PAY_URL,content);
                log.info("请求结果:"+responseStr);
                JSONObject PostResult=JSONObject.parseObject(responseStr);
                if(PostResult.get("code").toString().equals("1")){
                    JSONObject result=JSONObject.parseObject(PostResult.get("result").toString());
                    JSONObject payInfo=JSONObject.parseObject(result.get("payInfo").toString());
                    jsonObject.put("timeStamp",payInfo.get("timeStamp").toString());
                    jsonObject.put("nonceStr",payInfo.get("nonceStr").toString());
                    jsonObject.put("package",payInfo.get("package").toString());
                    jsonObject.put("signType",payInfo.get("signType").toString());
                    jsonObject.put("paySign",payInfo.get("paySign").toString());
                }else {
                    return   CommonResult.failed(PostResult.get("msg").toString());
                }
                /*WxConfig wxConfig = new WxConfig();
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
                map.put("body", "塔塔酒业");
                for (String key : map.keySet()){
                    System.out.println("key: "+ key + "; value: " + map.get(key));
                    map1.put(key,map.get(key));
                }
                map.put("sign",createSign("UTF-8",map1));
                log.info("---------------------------------支付信息 map "+map);
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
                jsonObject.put("paySign",createSign("UTF-8",map2));*/
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
    @Transactional(rollbackFor = Exception.class)
    public void wxNotify(HttpServletRequest request, HttpServletResponse response){
        System.out.println("==>进入微信支付回调");
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
                String orderSn=map.get("out_trade_no");
                String wxOrderNum=map.get("transaction_id");
                String openId = map.get("openid");
                OmsOrder omsOrder = orderMapper.selectByOrderNum(orderSn);
                if(omsOrder!=null & !omsOrder.getStatus().equals("0")){
                    //更新交易记录
                    omsOrder.setChannelOrderNum(wxOrderNum);//微信订单号
                    omsOrder.setModifyTime(new Date());
                    omsOrder.setStatus(1);
                    orderMapper.updateByPrimaryKey(omsOrder);
                }else {
                    Asserts.fail("==>订单不存在或订单状态异常");
                }
                UmsMember umsMember = umsMemberMapper.getUmsMemberByOpenId(openId);
                if(umsMember == null){
                    Asserts.fail("==>找不到 openId : "+openId+" 对应的用户信息");
                }
                List<OmsOrderItem> orderItemList = omsOrderItemService.getItemForOrderSn(orderSn);
                if(CollectionUtils.isEmpty(orderItemList)){
                    Asserts.fail("==>找不到订单号 :"+orderSn +"的订单商品信息");
                }
                //生成配送单
                WmsMember isWms=wmsMemberMapper.selectByUmsId(umsMember.getId());
                if(isWms!=null){
                    log.info("自身是配送中心");
                }
                Long  id= umsMemberService.getSuperiorDistributionCenterMemberId(umsMember.getId());
                log.info("找到的配送中心ID 为"+id);
                WmsMember wmsMember=null;
                if(id!=null){
                    wmsMember=wmsMemberMapper.selectByUmsId(id);
                    if (wmsMember==null){
                        Asserts.fail("==>找不到对应配送中心信息");
                    }
                }else {
                    Asserts.fail("==>找不到上级配送中心");
                }
                String address=omsOrder.getReceiverProvince()+omsOrder.getReceiverCity()+omsOrder.getReceiverRegion()+omsOrder.getReceiverDetailAddress();
                OmsDistribution distribution=new OmsDistribution();
                distribution.setOrderSn(omsOrder.getOrderSn());
                distribution.setStatus(0);//待配送
                distribution.setPhone(omsOrder.getReceiverPhone());
                distribution.setName(omsOrder.getReceiverName());
                distribution.setAddress(address);
                distribution.setCreateTime(new Date());
                distribution.setWmsMemberId(wmsMember.getId());
                distribution.setType(1);
                distribution.setSubPrice(omsOrder.getPayAmount());
                distribution.setUmsMemberId(umsMember.getId());
                distribution.setProfit(BigDecimal.ZERO);
                distributionMapper.insert(distribution);
                for(OmsOrderItem omsOrderItem : orderItemList){
                    if(isWms!=null) {
                        log.info("==》自身是配送中心，不生成配送单");
                        omsOrder.setStatus(2);
                        omsOrderItem.setDistributionStatus(2L);
                        distribution.setStatus(5);
                        omsOrder.setReceiveTime(new Date());
                        orderMapper.updateByPrimaryKey(omsOrder);
                        omsOrderItemMapper.updateByPrimaryKey(omsOrderItem);//更新订单详情
                        distributionMapper.updateByPrimaryKey(distribution);
                        //增加额度
                        PmsProduct pmsProduct=productMapper.selectByPrimaryKey(omsOrderItem.getProductId());
                        if(pmsProduct==null){
                            Asserts.fail("==>找不到商品信息");
                        }
                        BigDecimal subPrice=BigDecimal.ZERO;
                        switch (isWms.getLevel()){
                            case 1:
                                subPrice=pmsProduct.getDeliveryCenterProductValue().multiply(new BigDecimal(omsOrderItem.getProductQuantity()));
                                break;
                            case 2:
                                subPrice=pmsProduct.getRegionalProductValue().multiply(new BigDecimal(omsOrderItem.getProductQuantity()));
                                break;
                            case 3:
                                subPrice=pmsProduct.getWebmasterProductValue().multiply(new BigDecimal(omsOrderItem.getProductQuantity()));
                                break;
                        }
                        isWms.setCreditLine(isWms.getCreditLine().add(subPrice));
                        isWms.setUpdateTime(new Date());
                        wmsMemberMapper.updateByPrimaryKey(isWms);
                    }else {
                        if(omsOrderItem.getIfUpgradeDistributionCenterProduct() == 1){
                            log.info("==》升级配送中心商品，不生成配送单");
                            omsOrder.setStatus(2);
                            omsOrderItem.setDistributionStatus(2L);
                            omsOrder.setReceiveTime(new Date());
                            orderMapper.updateByPrimaryKey(omsOrder);
                            omsOrderItemMapper.updateByPrimaryKey(omsOrderItem);//更新订单详情
                            //升级配送中心商品 插入账户分佣流水
                            acctSettleInfoService.insertCommissionRecordFlow(umsMember,orderSn);
                        }else {
                            OmsDistributionItem distributionItem=new OmsDistributionItem();
                            distributionItem.setDistributionId(distribution.getId());
                            PmsProduct pmsProduct=productMapper.selectByPrimaryKey(omsOrderItem.getProductId());
                            if(omsOrderItem.getIfJoinVipProduct() == 1){
                                PmsProduct glProduct=null;//关联商品
                                if(pmsProduct==null){
                                    Asserts.fail("==>找不到商品信息");
                                }else {
                                    if(pmsProduct.getRelationProductId()==null||pmsProduct.getRelationProductNum()==null){
                                        Asserts.fail("==>未配置关联商品信息");
                                    }
                                    glProduct=productMapper.selectByPrimaryKey(pmsProduct.getRelationProductId());
                                    if(glProduct==null){
                                        Asserts.fail("==>未找到关联商品信息");
                                    }
                                }
                                log.info("==》升级商品，配送货物id:"+pmsProduct.getRelationProductId());
                                distributionItem.setGoodsImg(glProduct.getPic());
                                distributionItem.setGoodsTitle(glProduct.getName());
                                distributionItem.setGoodsSubtitle(glProduct.getSubTitle());
                                distributionItem.setPrice(glProduct.getOriginalPrice());
                                distributionItem.setNumber(pmsProduct.getRelationProductNum());
                                distributionItem.setSubPrice(glProduct.getOriginalPrice().multiply(new BigDecimal(pmsProduct.getRelationProductNum())));
                                distributionItem.setProductId(pmsProduct.getRelationProductId());
                                distributionItem.setProfit(glProduct.getDeliveryAmount().multiply(new BigDecimal(pmsProduct.getRelationProductNum())));//配送收益
                            }else {
                                distributionItem.setGoodsImg(omsOrderItem.getProductPic());
                                distributionItem.setGoodsTitle(omsOrderItem.getProductName());
                                distributionItem.setGoodsSubtitle(omsOrderItem.getPromotionName());
                                distributionItem.setPrice(omsOrderItem.getProductPrice());
                                distributionItem.setNumber(omsOrderItem.getProductQuantity());
                                distributionItem.setSubPrice(omsOrderItem.getProductPrice().multiply(new BigDecimal(omsOrderItem.getProductQuantity())));
                                distributionItem.setProductId(omsOrderItem.getProductId());
                                distributionItem.setProfit(pmsProduct.getDeliveryAmount().multiply(new BigDecimal(omsOrderItem.getProductQuantity())));//配送收益
                            }
                            distribution.setProfit(distribution.getProfit().add(distributionItem.getProfit()));
                            distributionItemMapper.insert(distributionItem);
                            omsOrderItem.setRelationDistributionId(distribution.getId());//关联id
                            omsOrderItem.setDistributionStatus(0L);//配送状态 待配送
                            omsOrderItemMapper.updateByPrimaryKey(omsOrderItem);//更新订单详情
                    }
                    }

                }
                distributionMapper.updateByPrimaryKey(distribution);//更新配送单收益
                //插入分佣流水
                if(isWms!=null) {
                    log.info("==》自身是配送中心，订单结束，开始分佣");
                    acctSettleInfoService.insertCommissionRecordFlow(umsMember,orderSn);
                }
                //acctSettleInfoService.insertCommissionRecordFlow(umsMember,orderSn);
                //会员等级提升到VIP用户
                for(OmsOrderItem omsOrderItem : orderItemList){
                    if(omsOrderItem.getIfJoinVipProduct() == 1){
                        umsMemberService.updateUmsMemberLevel(umsMember,StaticConstant.UMS_MEMBER_LEVEL_NAME_VIP_MEMBER,omsOrderItem);
                    }
                    if(omsOrderItem.getIfUpgradeDistributionCenterProduct() == 1){
                        umsMemberService.updateUmsMemberLevel(umsMember, StaticConstant.UMS_MEMBER_LEVEL_NAME_DELIVERY_CENTER,omsOrderItem);
                    }
                }
                //业务处理结束
            }
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 微信支付签名算法sign
     * @param characterEncoding
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters)throws Exception{
        WxConfig config = new WxConfig();
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
        sb.append("key=" + config.getKey());
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
