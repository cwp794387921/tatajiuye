package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.InventoryChangeRecord;
import com.tata.jiuye.model.InventoryChangeRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryChangeRecordMapper extends BaseMapper<InventoryChangeRecord> {
    long countByExample(InventoryChangeRecordExample example);

    int deleteByExample(InventoryChangeRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InventoryChangeRecord record);

    int insertSelective(InventoryChangeRecord record);

    List<InventoryChangeRecord> selectByExample(InventoryChangeRecordExample example);

    InventoryChangeRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InventoryChangeRecord record, @Param("example") InventoryChangeRecordExample example);

    int updateByExample(@Param("record") InventoryChangeRecord record, @Param("example") InventoryChangeRecordExample example);

    int updateByPrimaryKeySelective(InventoryChangeRecord record);

    int updateByPrimaryKey(InventoryChangeRecord record);
}
