package com.tata.jiuye.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.mapper.InventoryChangeRecordMapper;
import com.tata.jiuye.model.InventoryChangeRecord;
import com.tata.jiuye.portal.service.InventoryChangeRecordService;

import java.util.Date;

/**
 * 库存变动记录业务实现
 */
public class InventoryChangeRecordServiceImpl extends ServiceImpl<InventoryChangeRecordMapper, InventoryChangeRecord> implements InventoryChangeRecordService {


    @Override
    public void insertnventoryChangeRecord(Integer changeQuantity,String changeType,Integer quantityAfterChange,Integer quantityBeforeChange){
        InventoryChangeRecord record = new InventoryChangeRecord();
        record.setChangeNumber(changeQuantity);
        record.setChangeOfType(changeType);
        record.setCreateTime(new Date());
        record.setQuantityAfterChange(quantityAfterChange);
        record.setQuantityBeforeChange(quantityBeforeChange);
        this.save(record);
    }

}
