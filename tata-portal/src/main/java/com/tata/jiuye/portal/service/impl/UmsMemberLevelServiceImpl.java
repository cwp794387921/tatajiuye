package com.tata.jiuye.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tata.jiuye.common.exception.Asserts;
import com.tata.jiuye.mapper.UmsMemberLevelMapper;
import com.tata.jiuye.model.UmsMemberLevel;
import com.tata.jiuye.model.UmsMemberLevelExample;
import com.tata.jiuye.portal.service.UmsMemberLevelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员等级管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UmsMemberLevelServiceImpl extends ServiceImpl<UmsMemberLevelMapper, UmsMemberLevel> implements UmsMemberLevelService {

    private final UmsMemberLevelMapper memberLevelMapper;

    @Value("${umsmemberlevelname.deliverycenter}")
    private String UMS_MEMBER_LEVEL_NAME_DELIVERYCENTER;
    @Value("${umsmemberlevelname.ordinarymember}")
    private String UMS_MEMBER_LEVEL_NAME_ORDINARYMEMBER;
    @Override
    public List<UmsMemberLevel> list(Integer defaultStatus) {
        UmsMemberLevelExample example = new UmsMemberLevelExample();
        example.createCriteria().andDefaultStatusEqualTo(defaultStatus);
        return memberLevelMapper.selectByExample(example);
    }

    @Override
    public Boolean isSomeOneLevel(Long memberLevelId,String memberLevelName){
        log.info("--------------------------是否某个用户等级判断   开始--------------------------");
        if(memberLevelId == null){
            Asserts.fail("用户等级ID为空");
        }
        UmsMemberLevel umsMemberLevel = this.getById(memberLevelId);
        if(memberLevelName.equals(umsMemberLevel.getName())){
            log.info("--------------------------判断结果为 用户等级ID为: "+memberLevelId+"的用户,用户等级为"+memberLevelName);
            log.info("--------------------------是否某个用户等级判断   结束--------------------------");
            return true;
        }
        log.info("--------------------------判断结果为 用户等级ID为: "+memberLevelId+"的用户,用户等级不为"+memberLevelName);
        log.info("--------------------------是否某个用户等级判断   结束--------------------------");
        return false;
    }

    @Override
    public String getUmsMemberLevelName(Long memberLevelId){
        log.info("--------------------------获取指定用户的角色等级名称    开始--------------------------");
        if(memberLevelId == null){
            Asserts.fail("用户未绑定角色等级");
        }
        UmsMemberLevel  umsMemberLevel = getById(memberLevelId);
        log.info("--------------------------用户等级为"+umsMemberLevel);
        String levelName = umsMemberLevel.getName();
        log.info("--------------------------获取指定用户的角色等级名称    结束--------------------------");
        return levelName;
    }
}
