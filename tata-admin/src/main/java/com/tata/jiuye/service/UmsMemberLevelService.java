package com.tata.jiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.UmsMemberLevel;

import java.util.List;

/**
 * 会员等级管理Service
 * Created by macro on 2018/4/26.
 */
public interface UmsMemberLevelService extends IService<UmsMemberLevel> {

    /**
     * 获取所有会员登录
     * @param defaultStatus 是否为默认会员
     */
    List<UmsMemberLevel> list(Integer defaultStatus);


    /**
     * 判断用户等级是否为传入的名称
     * @param memberLevelId
     * @param memberLevelName
     * @return
     */
    Boolean isSomeOneLevel(Long memberLevelId,String memberLevelName);
}
