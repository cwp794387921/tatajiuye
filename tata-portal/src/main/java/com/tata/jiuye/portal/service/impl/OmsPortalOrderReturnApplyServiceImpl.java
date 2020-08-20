package com.tata.jiuye.portal.service.impl;

import com.tata.jiuye.mapper.OmsOrderReturnApplyMapper;
import com.tata.jiuye.model.OmsOrderReturnApply;
import com.tata.jiuye.portal.domain.OmsOrderReturnApplyParam;
import com.tata.jiuye.portal.service.OmsPortalOrderReturnApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 订单退货管理Service实现类
 * Created by macro on 2018/10/17.
 */
@Service
@RequiredArgsConstructor
public class OmsPortalOrderReturnApplyServiceImpl implements OmsPortalOrderReturnApplyService {

    private final OmsOrderReturnApplyMapper returnApplyMapper;

    @Override
    public int create(OmsOrderReturnApplyParam returnApply) {
        OmsOrderReturnApply realApply = new OmsOrderReturnApply();
        BeanUtils.copyProperties(returnApply,realApply);
        realApply.setCreateTime(new Date());
        realApply.setStatus(0);
        return returnApplyMapper.insert(realApply);
    }
}
