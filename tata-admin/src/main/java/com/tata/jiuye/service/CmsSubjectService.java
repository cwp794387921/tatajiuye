package com.tata.jiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.CmsSubject;

import java.util.List;

/**
 * 商品专题Service
 *
 * @author lewis
 */
public interface CmsSubjectService extends IService<CmsSubject> {

    /**
     * 查询所有专题
     */
    List<CmsSubject> listAll();

    /**
     * 分页查询专题
     */
    List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize);
}
