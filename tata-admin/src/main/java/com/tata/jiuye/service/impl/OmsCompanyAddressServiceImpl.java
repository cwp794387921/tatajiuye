package com.tata.jiuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.mapper.OmsCompanyAddressMapper;
import com.tata.jiuye.model.OmsCompanyAddress;
import com.tata.jiuye.model.OmsCompanyAddressExample;
import com.tata.jiuye.service.OmsCompanyAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址管理Service实现类
 *
 * @author lewis
 */
@Service
@RequiredArgsConstructor
public class OmsCompanyAddressServiceImpl extends ServiceImpl<OmsCompanyAddressMapper, OmsCompanyAddress> implements OmsCompanyAddressService {

    private final OmsCompanyAddressMapper companyAddressMapper;

    @Override
    public List<OmsCompanyAddress> list() {
        return companyAddressMapper.selectByExample(new OmsCompanyAddressExample());
    }
}
