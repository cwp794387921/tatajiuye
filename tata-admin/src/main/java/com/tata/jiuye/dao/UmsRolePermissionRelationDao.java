package com.tata.jiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.UmsPermission;
import com.tata.jiuye.model.UmsRolePermissionRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义角色权限关系管理Dao
 *
 * @author lewis
 */
public interface UmsRolePermissionRelationDao extends BaseMapper<UmsRolePermissionRelation> {

    /**
     * 批量插入角色和权限关系
     */
    int insertList(@Param("list")List<UmsRolePermissionRelation> list);

    /**
     * 根据角色获取权限
     */
    List<UmsPermission> getPermissionList(@Param("roleId") Long roleId);
}
