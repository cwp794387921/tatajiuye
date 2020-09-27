package com.tata.jiuye.controller;


import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.dto.OmsOrderQueryParam;
import com.tata.jiuye.model.OmsOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 首页相关(后台) Controller
 *
 * @author laichenhui
 */
@Controller
@Api(tags = "HomeRelatedController", description = "首页相关(后台)")
@RequestMapping("/homeRelated")
@RequiredArgsConstructor
public class HomeRelatedController {


}
