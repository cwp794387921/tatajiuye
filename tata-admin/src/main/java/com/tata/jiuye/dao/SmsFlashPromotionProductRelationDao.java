package com.tata.jiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.dto.SmsFlashPromotionProduct;
import com.tata.jiuye.model.SmsFlashPromotionProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义限时购商品关系管理Mapper
 *
 * @author lewis
 */
public interface SmsFlashPromotionProductRelationDao extends BaseMapper<SmsFlashPromotionProductRelation> {

    /**
     * 获取限时购及相关商品信息
     */
    List<SmsFlashPromotionProduct> getList(@Param("flashPromotionId") Long flashPromotionId, @Param("flashPromotionSessionId") Long flashPromotionSessionId);
}
