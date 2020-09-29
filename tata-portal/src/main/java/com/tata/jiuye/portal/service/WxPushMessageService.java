package com.tata.jiuye.portal.service;

/**
 * 小程序消息推送
 */
public interface WxPushMessageService {

    /**
     * 下级申请补货通知上级仓库
     * @param openid
     * @param orderSn
     * @return
     */
    String pushReplenishmentNotice(String openid, String orderSn);
}
