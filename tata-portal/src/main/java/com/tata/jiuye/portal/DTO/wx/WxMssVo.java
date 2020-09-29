package com.tata.jiuye.portal.DTO.wx;

import com.tata.jiuye.DTO.TemplateData;
import lombok.Data;

import java.util.Map;

/**
 * 订单发货通知
 */
@Data
public class WxMssVo {
    //用户openid
    private String touser;
    //模版id
    private String template_id;
    /**
     * 默认跳到小程序首页
     */
    private String page = "index";
    /**
     * 放大那个推送字段
     */
    private String emphasis_keyword = "keyword1.DATA";
    /**
     * 推送文字
     */
    private Map<String, TemplateData> data;
}
