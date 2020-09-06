package com.tata.jiuye.portal.controller;


import com.tata.jiuye.DTO.DirectPerformanceResult;
import com.tata.jiuye.DTO.IndirectPerformanceResult;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.portal.service.UmsMemberInviteRelationService;
import com.tata.jiuye.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "UmsMemberInviteController", description = "邀请业绩")
@RequestMapping("/invite")
@RequiredArgsConstructor
public class UmsMemberInviteController {

    @Autowired
    private UmsMemberInviteRelationService umsMemberInviteRelationService;
    @Autowired
    private UmsMemberService memberService;

    @ApiOperation("获取指定用户直邀团队业绩")
    @RequestMapping(value = "/getDirectPerformance", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getDirectPerformance(@RequestParam(required = false, defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                             @RequestParam(required = false, defaultValue = "10")@ApiParam("每页条数") Integer pageSize) {
        UmsMember umsMember = memberService.getCurrentMember();
        if(umsMember == null){
            return CommonResult.failed("用户未登录");
        }
        CommonPage<DirectPerformanceResult> result = umsMemberInviteRelationService.getDirectPerformance(pageNum,pageSize,umsMember.getId());
        return CommonResult.success(result);
    }


    @ApiOperation("获取指定用户间接邀请团队业绩")
    @RequestMapping(value = "/getIndirectPerformance", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getIndirectPerformance(@RequestParam(required = false, defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                             @RequestParam(required = false, defaultValue = "10")@ApiParam("每页条数") Integer pageSize) {
        UmsMember umsMember = memberService.getCurrentMember();
        if(umsMember == null){
            return CommonResult.failed("用户未登录");
        }
        CommonPage<IndirectPerformanceResult> result = umsMemberInviteRelationService.getInDirectPerformance(pageNum,pageSize,umsMember.getId());
        return CommonResult.success(result);
    }
}
