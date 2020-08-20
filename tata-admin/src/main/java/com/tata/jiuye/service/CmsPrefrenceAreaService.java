package com.tata.jiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tata.jiuye.model.CmsPrefrenceArea;

import java.util.List;

/**
 * 优选专区Service
 *
 * @author lewis
 */
public interface CmsPrefrenceAreaService extends IService<CmsPrefrenceArea> {

    /**
     * 获取所有优选专区
     */
    List<CmsPrefrenceArea> listAll();
}
