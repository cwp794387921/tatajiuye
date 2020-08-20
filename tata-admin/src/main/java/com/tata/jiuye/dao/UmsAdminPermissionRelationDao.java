package com.tata.jiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.UmsAdminPermissionRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义用户权限关系管理Mapper
 *
 * @author lewis
 */
public interface UmsAdminPermissionRelationDao extends BaseMapper<UmsAdminPermissionRelation> {

    /**
     * 批量创建
     */
    int insertList(@Param("list") List<UmsAdminPermissionRelation> list);
}
