package com.tata.jiuye.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.mapper.PmsBrandMapper;
import com.tata.jiuye.mapper.PmsProductMapper;
import com.tata.jiuye.model.PmsBrand;
import com.tata.jiuye.model.PmsProduct;
import com.tata.jiuye.model.PmsProductExample;
import com.tata.jiuye.portal.dao.HomeDao;
import com.tata.jiuye.portal.service.PortalBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 前台品牌管理Service实现类
 * Created by macro on 2020/5/15.
 */
@Service
@RequiredArgsConstructor
public class PortalBrandServiceImpl implements PortalBrandService {

    private final HomeDao homeDao;
    private final PmsBrandMapper brandMapper;
    private final PmsProductMapper productMapper;

    @Override
    public List<PmsBrand> recommendList(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return homeDao.getRecommendBrandList(offset, pageSize);
    }

    @Override
    public PmsBrand detail(Long brandId) {
        return brandMapper.selectByPrimaryKey(brandId);
    }

    @Override
    public CommonPage<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andDeleteStatusEqualTo(0)
                .andBrandIdEqualTo(brandId);
        List<PmsProduct> productList = productMapper.selectByExample(example);
        return CommonPage.restPage(productList);
    }
}
