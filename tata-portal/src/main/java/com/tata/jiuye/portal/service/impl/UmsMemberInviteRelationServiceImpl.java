package com.tata.jiuye.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.mapper.UmsMemberInviteRelationMapper;
import com.tata.jiuye.model.UmsMemberInviteRelation;
import com.tata.jiuye.portal.service.UmsMemberInviteRelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户邀请关系(绑定邀请关系业务实现)
 * @author laich
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UmsMemberInviteRelationServiceImpl extends ServiceImpl<UmsMemberInviteRelationMapper, UmsMemberInviteRelation> implements UmsMemberInviteRelationService {
    @Autowired
    private UmsMemberInviteRelationMapper umsMemberInviteRelationMapper;

}
