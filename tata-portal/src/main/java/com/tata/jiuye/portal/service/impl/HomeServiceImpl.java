package com.tata.jiuye.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.common.service.RedisService;
import com.tata.jiuye.mapper.*;
import com.tata.jiuye.model.*;
import com.tata.jiuye.portal.dao.HomeDao;
import com.tata.jiuye.portal.domain.FlashPromotionProduct;
import com.tata.jiuye.portal.domain.HomeContentResult;
import com.tata.jiuye.portal.domain.HomeFlashPromotion;
import com.tata.jiuye.portal.service.HomeService;
import com.tata.jiuye.portal.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 首页内容管理Service实现类
 * Created by macro on 2019/1/28.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final HomeDao homeDao;
    private final PmsProductMapper productMapper;
    private final CmsSubjectMapper subjectMapper;
    private final SmsHomeAdvertiseMapper advertiseMapper;
    private final SmsFlashPromotionMapper flashPromotionMapper;
    private final PmsProductCategoryMapper productCategoryMapper;
    private final SmsFlashPromotionSessionMapper promotionSessionMapper;
    @Resource
    private RedisService redisService;
    @Value("${pmsproductcategoryname.memberrepurchase}")
    private String PMS_PRODUCT_CATEGORY_NAME_MEMBERREPURCHASE;

    @Value("${umsmemberlevelname.vip}")
    private String UMS_MEMBER_LEVEL_NAME_VIP;
    @Override
    public HomeContentResult content() {

        HomeContentResult result = new HomeContentResult();
        //获取首页广告
        result.setAdvertiseList(getHomeAdvertiseList());
        //获取特殊商品"加入VIP"的商品ID
        Long upgradeToVipProductId = getProductByIfJoinVipProduct();
        result.setUpgradeToVipProductId(upgradeToVipProductId);
        //获取分类为"会员复购"的分类ID
        Long productCategoryId = getPmsProductCategoryIdByMemberRepurchase();
        result.setProductCategoryId(productCategoryId);
        //获取推荐品牌
        //result.setBrandList(homeDao.getRecommendBrandList(0, 6));
        //获取秒杀信息
        //result.setHomeFlashPromotion(getHomeFlashPromotion());
        //获取人气推荐(热卖)
        result.setHotProductList(homeDao.getHotProductList(0, 3));
        //获取新品推荐
        result.setNewProductList(homeDao.getNewProductList(0, 10));
        //获取推荐专题
        //result.setSubjectList(homeDao.getRecommendSubjectList(0, 4));
        return result;
    }

    @Override
    public List<area>queryAllCityName(){
        if(redisService.get("allCity")!=null){
            List<area> data=(List<area>)redisService.get("allCity");
            return  data;
        }
        List<area>  areas= homeDao.queryAllCityName();
        if (areas!=null){
            redisService.set("allCity",areas,60*60*24*7);//缓存7天
        }
        return areas;
    }

    @Override
    public List<area>queryAllareaName(String city){
        if(redisService.get(city)!=null){
            List<area> data=(List<area>)redisService.get(city);
            return  data;
        }
        List<area> areas= homeDao.queryAllareaName(city);
        if (areas!=null){
            redisService.set(city,areas,60*60*24*7);//缓存7天
        }
        return areas;
    }

    @Override
    public List<PmsProduct> recommendProductList(Integer pageSize, Integer pageNum) {
        // TODO: 2019/1/29 暂时默认推荐所有商品
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria()
                .andDeleteStatusEqualTo(0)
                .andPublishStatusEqualTo(1)
                .andRecommandStatusEqualTo(1);
        return productMapper.selectByExample(example);
    }

    @Override
    public List<PmsProductCategory> getProductCateList(Long parentId) {
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.createCriteria()
                .andShowStatusEqualTo(1)
                .andParentIdEqualTo(parentId);
        example.setOrderByClause("sort desc");
        return productCategoryMapper.selectByExample(example);
    }

    @Override
    public List<CmsSubject> getSubjectList(Long cateId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        CmsSubjectExample example = new CmsSubjectExample();
        CmsSubjectExample.Criteria criteria = example.createCriteria();
        criteria.andShowStatusEqualTo(1);
        if (cateId != null) {
            criteria.andCategoryIdEqualTo(cateId);
        }
        return subjectMapper.selectByExample(example);
    }

    @Override
    public List<PmsProduct> hotProductList(Integer pageNum, Integer pageSize) {
        int offset = pageSize * (pageNum - 1);
        return homeDao.getHotProductList(offset, pageSize);
    }

    @Override
    public List<PmsProduct> newProductList(Integer pageNum, Integer pageSize) {
        int offset = pageSize * (pageNum - 1);
        return homeDao.getNewProductList(offset, pageSize);
    }

    @Override
    public CommonPage<PmsProduct> getPmsProductByProductCategoryId(Integer pageNum, Integer pageSize, Long productCategoryId){
        log.info("-------------------------根据商品分类获取分页分类商品列表 开始-----------------------------------------------");
        if(productCategoryId == null){
            Asserts.fail("商品分类ID为空");
        }
        log.info("-------------------------参数 pageNum :"+pageNum);
        log.info("-------------------------参数 pageSize :"+pageSize);
        log.info("-------------------------参数 productCategoryId :"+productCategoryId);
        PageHelper.startPage(pageNum, pageSize);
        List<PmsProduct> categoryProductList = homeDao.getPmsProductByProductCategoryId(productCategoryId);
        log.info("-------------------------结果  :"+productCategoryId);
        CommonPage<PmsProduct> resulPage = CommonPage.restPage(categoryProductList);
        log.info("-------------------------根据商品分类获取分页分类商品列表 结束-----------------------------------------------");
        return resulPage;
    }
/*************************************公共方法 分界线******************************************************************/
/*************************************私有方法 分界线******************************************************************/
    private HomeFlashPromotion getHomeFlashPromotion() {
        HomeFlashPromotion homeFlashPromotion = new HomeFlashPromotion();
        //获取当前秒杀活动
        Date now = new Date();
        SmsFlashPromotion flashPromotion = getFlashPromotion(now);
        if (flashPromotion != null) {
            //获取当前秒杀场次
            SmsFlashPromotionSession flashPromotionSession = getFlashPromotionSession(now);
            if (flashPromotionSession != null) {
                homeFlashPromotion.setStartTime(flashPromotionSession.getStartTime());
                homeFlashPromotion.setEndTime(flashPromotionSession.getEndTime());
                //获取下一个秒杀场次
                SmsFlashPromotionSession nextSession = getNextFlashPromotionSession(homeFlashPromotion.getStartTime());
                if (nextSession != null) {
                    homeFlashPromotion.setNextStartTime(nextSession.getStartTime());
                    homeFlashPromotion.setNextEndTime(nextSession.getEndTime());
                }
                //获取秒杀商品
                List<FlashPromotionProduct> flashProductList = homeDao.getFlashProductList(flashPromotion.getId(), flashPromotionSession.getId());
                homeFlashPromotion.setProductList(flashProductList);
            }
        }
        return homeFlashPromotion;
    }

    //获取下一个场次信息
    private SmsFlashPromotionSession getNextFlashPromotionSession(Date date) {
        SmsFlashPromotionSessionExample sessionExample = new SmsFlashPromotionSessionExample();
        sessionExample.createCriteria()
                .andStartTimeGreaterThan(date);
        sessionExample.setOrderByClause("start_time asc");
        List<SmsFlashPromotionSession> promotionSessionList = promotionSessionMapper.selectByExample(sessionExample);
        if (!CollectionUtils.isEmpty(promotionSessionList)) {
            return promotionSessionList.get(0);
        }
        return null;
    }

    private List<SmsHomeAdvertise> getHomeAdvertiseList() {
        SmsHomeAdvertiseExample example = new SmsHomeAdvertiseExample();
        example.createCriteria().andTypeEqualTo(1).andStatusEqualTo(1);
        example.setOrderByClause("sort desc");
        return advertiseMapper.selectByExample(example);
    }

    //根据时间获取秒杀活动
    private SmsFlashPromotion getFlashPromotion(Date date) {
        Date currDate = DateUtil.getDate(date);
        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        example.createCriteria()
                .andStatusEqualTo(1)
                .andStartDateLessThanOrEqualTo(currDate)
                .andEndDateGreaterThanOrEqualTo(currDate);
        List<SmsFlashPromotion> flashPromotionList = flashPromotionMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(flashPromotionList)) {
            return flashPromotionList.get(0);
        }
        return null;
    }

    //根据时间获取秒杀场次
    private SmsFlashPromotionSession getFlashPromotionSession(Date date) {
        Date currTime = DateUtil.getTime(date);
        SmsFlashPromotionSessionExample sessionExample = new SmsFlashPromotionSessionExample();
        sessionExample.createCriteria()
                .andStartTimeLessThanOrEqualTo(currTime)
                .andEndTimeGreaterThanOrEqualTo(currTime);
        List<SmsFlashPromotionSession> promotionSessionList = promotionSessionMapper.selectByExample(sessionExample);
        if (!CollectionUtils.isEmpty(promotionSessionList)) {
            return promotionSessionList.get(0);
        }
        return null;
    }

    /**
     * 获取 会员复购 的分类ID
     * @return
     */
    private Long getPmsProductCategoryIdByMemberRepurchase(){
        log.info("------------------------------获取会员复购分类ID方法 开始------------------------------");
        List<PmsProductCategory> pmsProductCategories = productCategoryMapper.getProductCategoryByName(PMS_PRODUCT_CATEGORY_NAME_MEMBERREPURCHASE);
        if(CollectionUtils.isEmpty(pmsProductCategories)){
            Asserts.fail("查询不到分类名称为 '"+PMS_PRODUCT_CATEGORY_NAME_MEMBERREPURCHASE+"'的商品分类");
        }
        Long productCategoryId = pmsProductCategories.get(0).getId();
        log.info("------------------------------分类名称'"+PMS_PRODUCT_CATEGORY_NAME_MEMBERREPURCHASE+"' 对应的 分类ID 为 :"+productCategoryId);
        log.info("------------------------------获取会员复购分类ID方法 结束------------------------------");
        return productCategoryId;
    }

    /**
     * 获取加入VIP的商品ID
     * @return
     */
    private Long getProductByIfJoinVipProduct(){
        log.info("------------------------------获取加入VIP的商品ID方法 开始------------------------------");
        //获取所有升级为VIP商品
        Long ifJoinVipProductId = productMapper.getProductByIfJoinVipProduct();
        log.info("------------------------------加入VIP的商品ID 为 : "+ifJoinVipProductId);
        log.info("------------------------------获取加入VIP的商品ID方法 开始------------------------------");
        return ifJoinVipProductId;
    }

    /**
     * 获取加入VIP的商品ID
     * @return
     */
    private Long getProductByIfUpgradeDistributionCenterProduct(){
        log.info("------------------------------获取升级配送中心的商品ID方法 开始------------------------------");
        //获取所有升级为VIP商品
        Long ifUpgradeDistributionCenterProductId = productMapper.getProductByIfUpgradeDistributionCenterProduct();
        log.info("------------------------------升级配送中心的商品ID 为 : "+ifUpgradeDistributionCenterProductId);
        log.info("------------------------------获取升级配送中心的商品ID方法 开始------------------------------");
        return ifUpgradeDistributionCenterProductId;
    }
}
