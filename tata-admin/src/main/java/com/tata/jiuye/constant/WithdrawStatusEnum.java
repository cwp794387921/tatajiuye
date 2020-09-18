package com.tata.jiuye.constant;

import lombok.Getter;

/**
 * 消息队列枚举配置
 * Created by macro on 2018/9/14.
 */
@Getter
public enum WithdrawStatusEnum {
    /**
     * 待审核
     */
    PENDING("待审核",0),
    /**
     * 消息通知ttl队列
     */
    PASS("审核通过",1),

    REJECT("拒绝",2);
    /**
     * 状态名称
     */
    private String name;
    /**
     * 状态值
     */
    private Integer value;

    WithdrawStatusEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
