package com.tata.jiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.CmsPrefrenceAreaProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义优选和商品关系操作Mapper
 *
 * @author lewis
 */
public interface CmsPrefrenceAreaProductRelationDao extends BaseMapper<CmsPrefrenceAreaProductRelation> {

    /**
     * 批量创建
     */
    int insertList(@Param("list") List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList);
}
