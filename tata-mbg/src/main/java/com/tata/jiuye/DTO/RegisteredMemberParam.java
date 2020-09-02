package com.tata.jiuye.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisteredMemberParam {
    @ApiModelProperty("用户手机号")
    private String phone;

    @ApiModelProperty("邀请用户手机号")
    private String invitorPhone;

    @ApiModelProperty("用户信息")
    private String userInfoJson;

    @ApiModelProperty("微信小程序端CODE")
    private String wxCode;

    @ApiModelProperty("验证码")
    private String verificationCode;
}
