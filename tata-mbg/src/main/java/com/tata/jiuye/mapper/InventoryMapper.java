package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.Inventory;
import com.tata.jiuye.model.InventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryMapper extends BaseMapper<Inventory> {
    long countByExample(InventoryExample example);

    int deleteByExample(InventoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Inventory record);

    int insertSelective(Inventory record);

    List<Inventory> selectByExample(InventoryExample example);

    Inventory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Inventory record, @Param("example") InventoryExample example);

    int updateByExample(@Param("record") Inventory record, @Param("example") InventoryExample example);

    int updateByPrimaryKeySelective(Inventory record);

    int updateByPrimaryKey(Inventory record);
}
