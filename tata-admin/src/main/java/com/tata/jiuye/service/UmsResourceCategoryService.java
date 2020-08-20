package com.tata.jiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.UmsResourceCategory;

import java.util.List;

/**
 * 后台资源分类管理Service
 * Created by macro on 2020/2/5.
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {

    /**
     * 获取所有资源分类
     */
    List<UmsResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    int create(UmsResourceCategory umsResourceCategory);

    /**
     * 修改资源分类
     */
    int update(Long id, UmsResourceCategory umsResourceCategory);

    /**
     * 删除资源分类
     */
    int delete(Long id);
}
