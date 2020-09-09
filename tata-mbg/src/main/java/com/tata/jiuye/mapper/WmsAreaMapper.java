package com.tata.jiuye.mapper;

import com.tata.jiuye.model.WmsArea;
import com.tata.jiuye.model.WmsAreaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsAreaMapper {
    long countByExample(WmsAreaExample example);

    int deleteByExample(WmsAreaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WmsArea record);

    int insertSelective(WmsArea record);



    List<WmsArea> selectByExample(WmsAreaExample example);

    WmsArea selectByPrimaryKey(Integer id);

    WmsArea selectByParams(WmsArea wmsArea);

    int updateByExampleSelective(@Param("record") WmsArea record, @Param("example") WmsAreaExample example);

    int updateByExample(@Param("record") WmsArea record, @Param("example") WmsAreaExample example);

    int updateByPrimaryKeySelective(WmsArea record);

    int updateByPrimaryKey(WmsArea record);
}