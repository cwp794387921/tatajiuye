package com.tata.jiuye.portal.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.common.collect.Maps;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.common.enums.ServiceEnum;
import com.tata.jiuye.common.enums.TemplateCodeEnums;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.common.service.RedisService;
import com.tata.jiuye.common.utils.*;
import com.tata.jiuye.common.utils.Base64;
import com.tata.jiuye.mapper.*;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.common.constant.StaticConstant;
import com.tata.jiuye.portal.service.*;
import com.tata.jiuye.portal.util.MD5Util;
import com.tata.jiuye.portal.util.WxConfig;
import com.tata.jiuye.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import com.tata.jiuye.utils.AliyunSmsUtilCOPY;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private UmsMemberInviteRelationMapper umsMemberInviteRelationMapper;
    @Resource
    private PmsProductMapper productMapper;
    @Resource
    private AcctInfoService acctInfoService;
    @Resource
    private RedisService redisService;

    @Value("${redis.key.orderId}")
    private String REDIS_KEY_ORDER_ID;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.key.member}")
    private String REDIS_KEY_MEMBER;
    @Value("${umsmemberlevelname.vip}")
    private String UMS_MEMBER_LEVEL_NAME_VIP;
    @Value("${auth.wechat.appId}")
    private String APPID;
    @Value("${auth.wechat.pay.mchId}")
    private String MCHID;
    @Value("${auth.wechat.pay.notifyurl}")
    private String NOTIFYURL;
    @Resource
    private AliyunSmsUtilCOPY smsUtil;
    @Resource
    private PayService payService;

    @PostMapping("/refundApply")
    @ResponseBody
    public CommonResult refundApply(@RequestParam @ApiParam("订单编号")String orderNum,String refundMoney, HttpServletRequest httpRequest,
                                         HttpServletResponse httpResponse)throws ServletException, IOException {
        payService.refundApply(orderNum,refundMoney);
        Map<String,Object>params=new HashMap<>();
        params.put("orderNum",orderNum);
        OmsOrder omsOrder = orderMapper.selectByOrderNum(params);
        if(omsOrder!=null) {
            if(omsOrder.getStatus()==2){
                return CommonResult.failed("订单配送中不可退款");
            }
            if(omsOrder.getStatus()==3){
                return CommonResult.failed("订单已完成不可退款");
            }
            BigDecimal money = null;
            if (omsOrder.getPayAmount() == null) {
                return CommonResult.failed("订单金额不存在");
            } else {
                money = omsOrder.getPayAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            BigDecimal refund=new BigDecimal(refundMoney);
            log.info("付款金额："+money+",退款金额："+refund);
            if(money.compareTo(refund)==-1){
                return CommonResult.failed("退款金额不能大于付款金额");
            }
            try {
                Map<String,String> map = Maps.newHashMap();
                map.put("merchantNo", Config.MERCHANT_NO);
                map.put("refundAmount",refund.toString());
                map.put("merchantOrderNo", omsOrder.getOrderSn());
                map.put("merchantRefundNo","TK"+omsOrder.getOrderSn());
                map.put("notifyUrl",Config.RefundNotifyUrl);
                map.put("refundReason","退款");
                TreeMap<String, Object> sortedMap = new TreeMap<String, Object>(map);
                String sign = EncryUtil.handleRSA(sortedMap, Config.PRIVATE_KEY);
                map.put("sign", Base64Util.encodeByBase64(sign));
                String content = ChannelUtils.getContent(map);
                log.info("请求参数："+content);
                String responseStr = HttpRequestUtils.readContentFromPost(Config.Refund_URL,content);
                log.info("请求结果:"+responseStr);
                JSONObject response=JSONObject.parseObject(responseStr);
                if(response.get("code").toString().equals("1")){
                    //成功
                }else {
                    return CommonResult.failed(response.get("msg").toString());
                }
            }catch (Exception e){

            }
        }else {
            return CommonResult.failed("订单不存在");
        }
        return CommonResult.success("操作成功");
    }


    @PostMapping("/RefundNotify")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void RefundNotify(HttpServletRequest request, HttpServletResponse response){
        System.out.println("==>进入随行付退款回调");
        try {
            String resXml = "";
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");// 获取返回信息
            System.out.println(result);
            JSONObject resultJSON=JSONObject.parseObject(result);
            if(!resultJSON.get("code").toString().equals("1")){
                log.info(resultJSON.get("msg").toString());
            }else {
                result=resultJSON.get("result").toString();
                resultJSON=JSONObject.parseObject(result);
                String orderNum=resultJSON.get("platformOrderNo").toString();
                Map<String,Object>params=new HashMap<>();
                params.put("channelOrderNum",orderNum);
                OmsOrder omsOrder = orderMapper.selectByOrderNum(params);
                if(omsOrder!=null){
                    if(omsOrder.getStatus()==1){
                        log.info("==》更改订单状态，配送单状态");
                        OmsDistribution distribution=new OmsDistribution();
                        distribution.setOrderSn(omsOrder.getOrderSn());
                         distribution=distributionMapper.selectByParams(distribution);
                         if(distribution==null){
                             log.info("==》配送单不存在");
                         }else {
                             distribution.setStatus(5);
                             distributionMapper.updateByPrimaryKey(distribution);
                         }
                        List<OmsOrderItem> orderItemList = omsOrderItemService.getItemForOrderSn(omsOrder.getOrderSn());
                        for(OmsOrderItem omsOrderItem : orderItemList){
                            if(omsOrderItem.getIfJoinVipProduct() == 1){
                                log.info("==》升级VIP商品，进行用户等级回退");
                                UmsMember umsMember=umsMemberMapper.selectByPrimaryKey(omsOrder.getMemberId());
                                if(umsMember.getMemberLevelId().equals(2L)){
                                    log.info("==》已升级配送中心，取消等级回退");
                                    continue;
                                }
                                umsMember.setMemberLevelId(4L);
                                umsMemberMapper.updateByPrimaryKeySelective(umsMember);
                                String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + umsMember.getUsername();
                                redisService.del(key);
                            }
                        }

                    }
                    omsOrder.setStatus(6);//已退款
                    orderMapper.updateByPrimaryKey(omsOrder);
                }else {
                    log.info("==》订单号不存在");
                }
            }
            resXml="success";
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "刷新订单")
    @RequestMapping(value = "/refreshOrder", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult refreshOrder(@RequestParam @ApiParam("订单编号")String orderNum) {
        log.info("==>接收到刷新订单请求,订单号:"+orderNum);
        Map<String,Object>params=new HashMap<>();
        params.put("orderNum",orderNum);
        OmsOrder omsOrder = orderMapper.selectByOrderNum(params);
        if(omsOrder==null){
            return CommonResult.success("订单不存在");
        }
        String key=REDIS_DATABASE + ":" + REDIS_KEY_ORDER_ID + omsOrder.getOrderSn().substring(0,omsOrder.getOrderSn().length()-10);
        List<OmsOrderItem> itemList=omsOrderItemService.getItemForOrderSn(omsOrder.getOrderSn());
        omsOrder.setOrderSn(generateOrderSn(omsOrder.getSourceType(),omsOrder.getPayType()));
        for (OmsOrderItem item:itemList){
            item.setOrderSn(omsOrder.getOrderSn());
            omsOrderItemMapper.updateByPrimaryKey(item);
        }
        orderMapper.updateByPrimaryKey(omsOrder);
        redisService.del(key);
        log.info("==>刷新订单号:"+omsOrder.getOrderSn());
        return CommonResult.success("操作成功");
    }

    private String generateOrderSn(Integer sourceType,Integer payType) {
        StringBuilder sb = new StringBuilder();
        //获取秒数
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        //获取毫秒数
        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String date = milliSecond.toString();
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ORDER_ID + date;
        Long increment = redisService.incr(key, 1);
        sb.append(date);
        sb.append(String.format("%02d", sourceType));
        sb.append(String.format("%02d", payType));
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }


    @ApiOperation("微信web支付接口")
    @PostMapping("/wxWebRechargePay")
    @ResponseBody
    public CommonResult wxWebRechargePay(@RequestParam @ApiParam("订单编号")String orderNum, HttpServletRequest httpRequest,
                                         HttpServletResponse httpResponse)throws ServletException, IOException {
        log.info("===========微信web支付:进入============");
        log.info("===========用户请求IP:{}============",getIp(httpRequest));
        if(StrUtil.isEmpty(orderNum)){
            return CommonResult.validateFailed("参数错误");
        }
        UmsMember member = umsMemberService.getCurrentMember();
        member=umsMemberMapper.selectByPrimaryKey(member.getId());
        String openId=member.getOpenid();
        if (openId==null){
            return CommonResult.failed("获取openid失败");
        }
        Map<String,Object>params=new HashMap<>();
        params.put("orderNum",orderNum);
        OmsOrder omsOrder = orderMapper.selectByOrderNum(params);
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
                map.put("com/tata/jiuye/service", ServiceEnum.WECHAT_APPLET.getValue().toString());
                map.put("orderTitle","塔塔酒业");
                map.put("notifyUrl",Config.NotifyUrl);
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("userId",openId);
                jsonObject1.put("orderType","wechatJs");
                jsonObject1.put("subAppid",Config.APPID);
                map.put("attach",jsonObject1.toJSONString());
                map.put("merchantOrderNo",orderNum);
                map.put("whetherSeparate","00");//00分账  01不分账
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
               /* WxConfig wxConfig = new WxConfig();
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

    private static boolean checkSign(String content, String sign, String publicKey) {
        try	{
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode2(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


            Signature signature = Signature
                    .getInstance("SHA1WithRSA");

            signature.initVerify(pubKey);
            signature.update( content.getBytes("utf-8") );

            boolean bverify = signature.verify( Base64.decode2(sign) );
            return bverify;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return false;
    }


    public boolean paysuccess(String orderSn,String wxOrderNum){
        Map<String,Object>params=new HashMap<>();
        params.put("orderNum",orderSn);
        OmsOrder omsOrder = orderMapper.selectByOrderNum(params);
        if(omsOrder!=null & !omsOrder.getStatus().equals("0")){
            //更新交易记录
            omsOrder.setChannelOrderNum(wxOrderNum);//渠道订单号
            omsOrder.setModifyTime(new Date());
            omsOrder.setStatus(1);
            orderMapper.updateByPrimaryKey(omsOrder);
        }else {
            Asserts.fail("==>订单不存在或订单状态异常");
        }
        UmsMember umsMember = umsMemberMapper.selectByPrimaryKey(omsOrder.getMemberId());
        if(umsMember == null){
            Asserts.fail("==>找不到用户信息");
        }
        List<OmsOrderItem> orderItemList = omsOrderItemService.getItemForOrderSn(orderSn);
        if(CollectionUtils.isEmpty(orderItemList)){
            Asserts.fail("==>找不到订单号 :"+orderSn +"的订单商品信息");
        }
        //生成配送单
        Map<String,Object>queryParams=new HashMap<>();
        queryParams.put("umsMemberId",umsMember.getId());
        WmsMember isWms=wmsMemberMapper.selectByUmsId(queryParams);
        if(isWms!=null){
            log.info("自身是配送中心");
        }
        Long  id= umsMemberService.getSuperiorDistributionCenterMemberId(umsMember.getId());
        log.info("找到的配送中心ID 为"+id);
        WmsMember wmsMember=null;
        if(id!=null){
            queryParams=new HashMap<>();
            queryParams.put("umsMemberId",id);
            wmsMember=wmsMemberMapper.selectByUmsId(queryParams);
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
                omsOrder.setStatus(3);//完成
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
                    omsOrder.setStatus(3);
                    omsOrderItem.setDistributionStatus(2L);
                    distribution.setStatus(5);
                    omsOrder.setReceiveTime(new Date());
                    orderMapper.updateByPrimaryKey(omsOrder);
                    omsOrderItemMapper.updateByPrimaryKey(omsOrderItem);//更新订单详情
                    distributionMapper.updateByPrimaryKey(distribution);
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
                        distributionItem.setPrice(glProduct.getPrice());
                        distributionItem.setNumber(pmsProduct.getRelationProductNum());
                        distributionItem.setSubPrice(glProduct.getPrice().multiply(new BigDecimal(pmsProduct.getRelationProductNum())));
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
                    //发送通知配送短信
                    if(!wmsMember.getId().equals(1L)){
                        JSONObject param=new JSONObject();
                        param.put("nickName", wmsMember.getNickname());
                        param.put("productName", distributionItem.getGoodsTitle());
                        param.put("productNum", distributionItem.getNumber());
                        param.put("custmer", distribution.getName());
                        param.put("receiver", distribution.getName());
                        param.put("address", distribution.getAddress());
                        param.put("time", DateUtil.now());
                        log.info("==》开始发送通知上级短信");
                        smsUtil.sendSms(wmsMember.getPhone(), TemplateCodeEnums.PS.getValue(),param.toString());
                    }else {
                        log.info("==》上级配送中心是平台，不发送短信");
                    }
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
        //发送会员下单通知
        //查找上级
        UmsMemberInviteRelation umsMemberInviteRelation = umsMemberInviteRelationMapper.getByMemberId(umsMember.getId());
        if (umsMemberInviteRelation != null&&!umsMemberInviteRelation.getFatherMemberId().equals(1L)) {
            UmsMember fatherInfo=umsMemberMapper.selectByPrimaryKey(umsMemberInviteRelation.getFatherMemberId());
            if(fatherInfo==null){
                log.info("==》上级信息不存在");
            }else{
                JSONObject param=new JSONObject();
                param.put("nickName", fatherInfo.getNickname());
                param.put("ammout", omsOrder.getPayAmount());
                param.put("orderSn", omsOrder.getOrderSn());
                param.put("custmer", omsOrder.getReceiverName());
                param.put("time", DateUtil.now());
                log.info("==》开始发送通知上级短信");
                smsUtil.sendSms(fatherInfo.getPhone(), TemplateCodeEnums.XD.getValue(),param.toString());
            }
        }else {
            log.info("==》上级不存在或绑定平台，取消发送短信");
        }
        //业务处理结束
        return true;
    }



    @PostMapping("/Notify")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void Notify(HttpServletRequest request, HttpServletResponse response){
        System.out.println("==>进入随行付支付回调");
        try {
            String resXml = "";
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");// 获取返回信息
            System.out.println(result);
            JSONObject resultJson=JSONObject.parseObject(result);
            if(resultJson.get("code").toString().equals("1")){
                System.out.println("交易成功");
                result=resultJson.get("result").toString();
                resultJson=JSONObject.parseObject(result);
                if(resultJson.get("tradeStatus").toString().equals("PAY_SUCCESS")){
                    //业务处理开始
                    String orderSn=resultJson.get("merchantOrderNo").toString();
                    String wxOrderNum=resultJson.get("platformOrderNo").toString();
                    if(!paysuccess(orderSn,wxOrderNum)){
                        Asserts.fail("==>业务处理失败");
                    }
                    resXml="success";
                }
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
            System.out.println(result);
            Map<String, String> map = WXPayUtil.xmlToMap(result);
            if (wxPay.isPayResultNotifySignatureValid(map)) {
                System.out.println("微信支付-签名验证成功");
                System.out.println(map.toString());
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                //业务处理开始
                String orderSn=map.get("out_trade_no");
                String wxOrderNum=map.get("transaction_id");
                if(!paysuccess(orderSn,wxOrderNum)){
                    Asserts.fail("==>业务处理失败");
                }
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
