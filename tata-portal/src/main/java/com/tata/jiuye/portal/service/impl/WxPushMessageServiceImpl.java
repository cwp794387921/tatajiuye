package com.tata.jiuye.portal.service.impl;

import com.tata.jiuye.DTO.TemplateData;
import com.tata.jiuye.mapper.OmsDistributionMapper;
import com.tata.jiuye.model.OmsDistribution;
import com.tata.jiuye.portal.DTO.wx.WxMssVo;
import com.tata.jiuye.portal.service.OmsOrderItemService;
import com.tata.jiuye.portal.service.OmsPortalOrderService;
import com.tata.jiuye.portal.service.WxPushMessageService;
import com.tata.jiuye.portal.util.inviteQrCode.InviteQrCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WxPushMessageServiceImpl implements WxPushMessageService {

    //用来请求微信的get和post

    @Autowired
    private OmsPortalOrderService omsPortalOrderService;
    @Autowired
    private OmsOrderItemService omsOrderItemService;
    @Autowired
    private OmsDistributionMapper omsDistributionMapper;

    /*
     * 下级申请补货通知上级仓库
     *
     * */
    @Override
    public String pushReplenishmentNotice(String openid, String orderSn) {
        //获取access_token
        String access_token = null;
        try {
            access_token = InviteQrCode.postToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send" +
                "?access_token=" + access_token;

        //拼接推送的模版
        WxMssVo wxMssVo = new WxMssVo();
        //用户openid
        wxMssVo.setTouser(openid);
        //模版id
        wxMssVo.setTemplate_id("D_MPrgXAmSzKAMqBfPdblZsSP3Z_-tDP7g1UTUBfqeU");
        //formid
        Map<String, TemplateData> m = new HashMap<>(5);
        //keyword1：商品名称，keyword2：商品数量，keyword3：收货人，keyword4：收货地址
        TemplateData thing2 = new TemplateData();
        //补货单
        OmsDistribution omsDistribution = omsDistributionMapper.getByOrderSn(orderSn);
        //商品名称
        thing2.setValue(omsDistribution.getGoodsTitle());
        m.put("thing2", thing2);

        //商品数量
        TemplateData number16 = new TemplateData();
        number16.setValue(omsDistribution.getNumber().toString());
        m.put("number16", number16);
        wxMssVo.setData(m);

        //收货人
        TemplateData name12 = new TemplateData();
        name12.setValue(omsDistribution.getName());
        m.put("name12", name12);
        wxMssVo.setData(m);

        //收货人地址
        TemplateData thing11 = new TemplateData();
        thing11.setValue(omsDistribution.getAddress());
        m.put("thing11", thing11);
        wxMssVo.setData(m);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, wxMssVo, String.class);
        log.error("小程序推送结果={}", responseEntity.getBody());
        return responseEntity.getBody();
    }
}
