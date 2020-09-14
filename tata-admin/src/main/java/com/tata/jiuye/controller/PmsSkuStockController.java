package com.tata.jiuye.controller;

import com.tata.jiuye.common.api.CommonResult;
import com.tata.jiuye.model.PmsSkuStock;
import com.tata.jiuye.service.PmsSkuStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * sku库存Controller
 *
 * @author lewis
 */
@Controller
@Api(tags = "PmsSkuStockController", description = "sku商品库存管理")
@RequestMapping("/sku")
@RequiredArgsConstructor
public class PmsSkuStockController {

    private final PmsSkuStockService skuStockService;
    @Autowired
    private PmsSkuStockService pmsSkuStockService;
    @ApiOperation("根据商品编号及编号模糊搜索sku库存")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsSkuStock>> getList(@RequestParam(required = false)@ApiParam("商品ID") Long pid, @RequestParam(required = false) String keyword) {
        List<PmsSkuStock> skuStockList = skuStockService.getList(pid, keyword);
        return CommonResult.success(skuStockList);
    }

    @ApiOperation("批量更新库存信息")
    @RequestMapping(value ="/update/{pid}",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long pid,@RequestBody List<PmsSkuStock> skuStockList){
        int count = skuStockService.update(pid,skuStockList);
        if(count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改库存数量")
    @RequestMapping(value ="/changeSkuStockNum",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult changeSkuStockNum(@RequestParam @ApiParam("商品id")Long productId,@RequestParam @ApiParam("库存变更数量")Integer changeNum,
                                          @RequestParam @ApiParam("库存变更类型(OUTOFSTOCK->出库,WAREHOUSING->入库)")String operationType,@RequestParam(required = false) @ApiParam("补货单号")String replenishmentOrderNo){
        pmsSkuStockService.changeSkuStockNum(productId,changeNum,operationType,replenishmentOrderNo);
        return CommonResult.success("修改库存成功");
    }

}
