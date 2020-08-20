package com.tata.jiuye.dao;

import com.tata.jiuye.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义商品满减Dao
 *
 * @author lewis
 */
public interface PmsProductFullReductionDao {

    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsProductFullReduction> productFullReductionList);
}
