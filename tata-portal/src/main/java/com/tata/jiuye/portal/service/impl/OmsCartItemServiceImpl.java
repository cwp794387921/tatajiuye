package com.tata.jiuye.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.OmsCartItemMapper;
import com.tata.jiuye.mapper.PmsProductMapper;
import com.tata.jiuye.model.OmsCartItem;
import com.tata.jiuye.model.OmsCartItemExample;
import com.tata.jiuye.model.PmsProduct;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.portal.dao.PortalProductDao;
import com.tata.jiuye.portal.domain.CartProduct;
import com.tata.jiuye.portal.domain.CartPromotionItem;
import com.tata.jiuye.portal.service.OmsCartItemService;
import com.tata.jiuye.portal.service.OmsPromotionService;
import com.tata.jiuye.portal.service.UmsMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车管理Service实现类
 * Created by macro on 2018/8/2.
 */
@Service
@RequiredArgsConstructor
public class OmsCartItemServiceImpl implements OmsCartItemService {
    @Resource
    private  PortalProductDao productDao;
    @Resource
    private  UmsMemberService memberService;
    @Resource
    private  OmsCartItemMapper cartItemMapper;
    @Resource
    private  OmsPromotionService promotionService;
    @Resource
    private PmsProductMapper pmsProductMapper;


    @Override
    public int add(Long productId,Long productSkuId,Integer quantity,UmsMember currentMember) {
        int count;
        if(productId == null){
            Asserts.fail("请选择加入购物车的商品");
        }
        PmsProduct pmsProduct = pmsProductMapper.selectByPrimaryKey(productId);
        if(pmsProduct == null){
            Asserts.fail("找不到商品");
        }
        OmsCartItem cartItem = new OmsCartItem();
        cartItem.setProductId(productId);
        cartItem.setProductName(pmsProduct.getName());
        cartItem.setProductPic(pmsProduct.getPic());
        cartItem.setProductBrand(pmsProduct.getBrandName());
        cartItem.setProductSn(pmsProduct.getProductSn());
        cartItem.setPrice(pmsProduct.getPrice());
        cartItem.setQuantity(quantity);
        cartItem.setProductSkuId(productSkuId);
        cartItem.setProductCategoryId(pmsProduct.getProductCategoryId());
        cartItem.setDirectPushAmount(pmsProduct.getDirectPushAmount());
        cartItem.setIndirectPushAmount(pmsProduct.getIndirectPushAmount());
        cartItem.setDeliveryAmount(pmsProduct.getDeliveryAmount());
        cartItem.setIfJoinVipProduct(pmsProduct.getIfJoinVipProduct());
        cartItem.setIfUpgradeDistributionCenterProduct(pmsProduct.getIfUpgradeDistributionCenterProduct());
        cartItem.setMemberId(currentMember.getId());
        cartItem.setMemberNickname(currentMember.getNickname());
        cartItem.setDeleteStatus(0);
        OmsCartItem existCartItem = getCartItem(cartItem);
        if (existCartItem == null) {
            cartItem.setCreateDate(new Date());
            count = cartItemMapper.insert(cartItem);
        } else {
            cartItem.setModifyDate(new Date());
            existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
            count = cartItemMapper.updateByPrimaryKey(existCartItem);
        }
        return count;
    }

    /**
     * 根据会员id,商品id和规格获取购物车中商品
     */
    private OmsCartItem getCartItem(OmsCartItem cartItem) {
        OmsCartItemExample example = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria().andMemberIdEqualTo(cartItem.getMemberId())
                .andProductIdEqualTo(cartItem.getProductId()).andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(cartItem.getProductSkuId())) {
            criteria.andProductSkuIdEqualTo(cartItem.getProductSkuId());
        }
        List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(cartItemList)) {
            return cartItemList.get(0);
        }
        return null;
    }

    @Override
    public List<OmsCartItem> list(Long memberId) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andMemberIdEqualTo(memberId);
        return cartItemMapper.selectByExample(example);
    }

    @Override
    public List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds) {
        List<OmsCartItem> cartItemList = list(memberId);
        if (CollUtil.isNotEmpty(cartIds)) {
            cartItemList = cartItemList.stream().filter(item -> cartIds.contains(item.getId())).collect(Collectors.toList());
        }
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(cartItemList)) {
            cartPromotionItemList = promotionService.calcCartPromotion(cartItemList);
        }
        return cartPromotionItemList;
    }

    @Override
    public int updateQuantity(Long id, Long memberId, Integer quantity) {
        OmsCartItem cartItem = new OmsCartItem();
        cartItem.setQuantity(quantity);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0)
                .andIdEqualTo(id).andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(cartItem, example);
    }

    @Override
    public int delete(Long memberId, List<Long> ids) {
        OmsCartItem record = new OmsCartItem();
        record.setDeleteStatus(1);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andIdIn(ids).andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(record, example);
    }

    @Override
    public CartProduct getCartProduct(Long productId) {
        return productDao.getCartProduct(productId);
    }

    @Override
    public int updateAttr(OmsCartItem cartItem,UmsMember umsMember) {
        //删除原购物车信息
        OmsCartItem updateCart = new OmsCartItem();
        updateCart.setId(cartItem.getId());
        updateCart.setModifyDate(new Date());
        updateCart.setDeleteStatus(1);
        cartItemMapper.updateByPrimaryKeySelective(updateCart);
        cartItem.setId(null);
        add(cartItem,umsMember);
        return 1;
    }


    public int add(OmsCartItem cartItem,UmsMember currentMember) {
        int count;
        //UmsMember currentMember = memberService.getCurrentMember();
        cartItem.setMemberId(currentMember.getId());
        cartItem.setMemberNickname(currentMember.getNickname());
        cartItem.setDeleteStatus(0);
        OmsCartItem existCartItem = getCartItem(cartItem);
        if (existCartItem == null) {
            cartItem.setCreateDate(new Date());
            count = cartItemMapper.insert(cartItem);
        } else {
            cartItem.setModifyDate(new Date());
            existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
            count = cartItemMapper.updateByPrimaryKey(existCartItem);
        }
        return count;
    }

    @Override
    public int clear(Long memberId) {
        OmsCartItem record = new OmsCartItem();
        record.setDeleteStatus(1);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(record, example);
    }
}
