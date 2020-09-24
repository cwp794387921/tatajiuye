package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.WmsArea;
import com.tata.jiuye.model.WmsAreaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WmsAreaMapper extends BaseMapper<WmsArea> {
    long countByExample(WmsAreaExample example);

    int deleteByExample(WmsAreaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WmsArea record);

    int insertSelective(WmsArea record);

    List<WmsArea> queryList(WmsArea area);

    List<WmsArea> queryProvince();

    List<WmsArea> queryCity();

    List<WmsArea> queryArea();

    List<WmsArea> selectByExample(WmsAreaExample example);

    WmsArea selectByPrimaryKey(Integer id);

    WmsArea selectByParams(WmsArea wmsArea);

    int updateByExampleSelective(@Param("record") WmsArea record, @Param("example") WmsAreaExample example);

    int updateByExample(@Param("record") WmsArea record, @Param("example") WmsAreaExample example);

    int updateByPrimaryKeySelective(WmsArea record);

    int updateByPrimaryKey(WmsArea record);
}
