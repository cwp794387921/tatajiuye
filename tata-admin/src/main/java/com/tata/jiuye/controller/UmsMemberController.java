package com.tata.jiuye.controller;


import com.github.pagehelper.PageHelper;
import com.tata.jiuye.DTO.UmsMemberQueryParam;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.mapper.UmsMemberMapper;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.utils.HttpTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户模块
 */
@Controller
@Api(tags = "UmsMemberController", description = "用户模块")
@RequestMapping("/umsMember")
@RequiredArgsConstructor
public class UmsMemberController {

    @Autowired
    private UmsMemberMapper umsMemberMapper;
    @Value("${requestempleurl}")
    private String REQUEST_TEMPLATE_URL;

    @ApiOperation("获取用户信息")
    @RequestMapping(value ="/getUmsMemberPageByParam",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getUmsMemberPageByParam(@RequestBody UmsMemberQueryParam param){
        PageHelper.startPage(param.getPageNum(),param.getPageSize());
        List<UmsMember> umsMembers = umsMemberMapper.getUmsMemberByParam(param);
        CommonPage<UmsMember> umsMemberCommonPage = CommonPage.restPage(umsMembers);
        return CommonResult.success(umsMemberCommonPage);
    }

    @ApiOperation("修改用户账号启用状态")
    @RequestMapping(value ="/updateUmsMemberStatus",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateUmsMemberStatus(@RequestParam @ApiParam("帐号启用状态:0->禁用；1->启用") Integer status,@RequestParam @ApiParam("选中的用户ID")Long memberId){
        if(0 != status && 1 != status){
            return CommonResult.failed("请传入有效的账号启用状态");
        }
        if(memberId == null){
            return CommonResult.failed("请选中用户");
        }
        UmsMember umsMember = umsMemberMapper.selectByPrimaryKey(memberId);
        if(!status.equals(umsMember.getStatus())){
            umsMember.setStatus(status);
            umsMemberMapper.updateByPrimaryKeySelective(umsMember);
            //清除缓存
            String url = "sso/delUmsCash";
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("memberId", memberId);
            CommonResult commonResult = HttpTools.sendPostRequest(REQUEST_TEMPLATE_URL + url, param);
        }
        return CommonResult.success("修改用户账号启用状态成功");
    }
}
