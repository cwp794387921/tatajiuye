package com.tata.jiuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.mapper.OmsCartItemMapper;
import com.tata.jiuye.model.OmsCartItem;
import com.tata.jiuye.model.OmsCartItemExample;
import com.tata.jiuye.model.PmsProduct;
import com.tata.jiuye.service.OmsCartItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class OmsCartItemServiceImpl extends ServiceImpl<OmsCartItemMapper, OmsCartItem> implements OmsCartItemService {
    @Autowired
    private OmsCartItemMapper cartItemMapper;

    @Override
    public void updateOmsCartItems(List<OmsCartItem> omsCartItems,PmsProduct product) {
        if(!CollectionUtils.isEmpty(omsCartItems)){
            for(OmsCartItem cartItem : omsCartItems){
                cartItem.setProductName(product.getName());
                cartItem.setProductPic(product.getPic());
                cartItem.setProductBrand(product.getBrandName());
                cartItem.setProductSn(product.getProductSn());
                cartItem.setPrice(product.getPrice());
                cartItem.setProductCategoryId(product.getProductCategoryId());
                cartItem.setDirectPushAmount(product.getDirectPushAmount());
                cartItem.setIndirectPushAmount(product.getIndirectPushAmount());
                cartItem.setDeliveryAmount(product.getDeliveryAmount());
                cartItem.setIfJoinVipProduct(product.getIfJoinVipProduct());
                cartItem.setIfUpgradeDistributionCenterProduct(product.getIfUpgradeDistributionCenterProduct());
                cartItem.setVipPrice(product.getVipPrice());
                cartItem.setDeliveryCenterPrice(product.getDeliveryCenterPrice());
                cartItemMapper.updateByPrimaryKeySelective(cartItem);
            }
        }
    }

    @Override
    public List<OmsCartItem> getByProductId(Long productId) {
        if(productId == null){
            return new ArrayList<OmsCartItem>();
        }
        OmsCartItemExample example = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(productId).andDeleteStatusEqualTo(0);
        List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(example);
        return cartItemList;
    }
}
