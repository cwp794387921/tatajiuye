package com.tata.jiuye.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.InventoryChangeRecord;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存变动记录业务
 */
public interface InventoryChangeRecordService extends IService<InventoryChangeRecord> {

    /**
     * 插入库存变动记录表
     * @param changeQuantity
     * @param changeType
     * @param quantityAfterChange
     * @param quantityBeforeChange
     */
    @Transactional
    void insertnventoryChangeRecord(Integer changeQuantity,String changeType,Integer quantityAfterChange,Integer quantityBeforeChange);
}
