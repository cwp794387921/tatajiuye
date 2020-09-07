package com.tata.jiuye.portal.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.mapper.AcctInfoMapper;
import com.tata.jiuye.mapper.WmsMemberMapper;
import com.tata.jiuye.model.AcctInfo;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.model.WmsMember;
import com.tata.jiuye.portal.service.UmsMemberService;
import com.tata.jiuye.portal.service.WmsMerberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.security.Principal;

@Controller
@Api(tags = "WmsMemberController", description = "配送中心用户管理")
@RequestMapping("/wms")
@RequiredArgsConstructor
public class WmsMemberController {

    private static final Logger log = LoggerFactory.getLogger(WmsMemberController.class);


    @Resource
    private WmsMerberService wmsMerberService;

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult info() {
        JSONObject result= wmsMerberService.selectMerberInfo();
        log.debug(result.toString());
        return CommonResult.success(result);
    }

}
