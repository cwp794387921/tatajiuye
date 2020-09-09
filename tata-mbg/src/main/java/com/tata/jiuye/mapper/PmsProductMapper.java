package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.PmsProduct;
import com.tata.jiuye.model.PmsProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductMapper extends BaseMapper<PmsProduct> {

    long countByExample(PmsProductExample example);

    int deleteByExample(PmsProductExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProduct record);

    int insertSelective(PmsProduct record);

    List<PmsProduct> selectByExampleWithBLOBs(PmsProductExample example);

    List<PmsProduct> selectByExample(PmsProductExample example);

    PmsProduct selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProduct record, @Param("example") PmsProductExample example);

    int updateByExampleWithBLOBs(@Param("record") PmsProduct record, @Param("example") PmsProductExample example);

    int updateByExample(@Param("record") PmsProduct record, @Param("example") PmsProductExample example);

    int updateByPrimaryKeySelective(PmsProduct record);

    int updateByPrimaryKeyWithBLOBs(PmsProduct record);

    int updateByPrimaryKey(PmsProduct record);

    /**
     * 根据条件查找商品列表
     * @param pmsProduct
     * @return
     */
    List<PmsProduct> queryList(PmsProduct pmsProduct);

    /**
     * 查找升级VIP需要购买的商品
     * @return
     */
    Long getProductByIfJoinVipProduct();

    /**
     * 查找升级配送中心需要购买的商品
     * @return
     */
    Long getProductByIfUpgradeDistributionCenterProduct();

    /**
     * 获取升级VIP的商品分类ID集合
     * @return
     */
    List<Long> getProductByIfJoinVipCategoryId();

    /**
     * 获取升级为配置中心的商品分类ID集合
     * @return
     */
    List<Long> getProductByIfUpgradeDistributionCenterCategoryId();
}
