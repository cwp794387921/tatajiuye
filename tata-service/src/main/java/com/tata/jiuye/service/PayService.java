package com.tata.jiuye.service;

import org.springframework.stereotype.Service;

@Service
public interface PayService {
      void refundApply(String orderNum,String refundMoney);
}
