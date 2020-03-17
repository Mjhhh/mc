package com.edu.ucenter.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author Admin
 */
@Mapper
public interface McPermissionMapper {
    /**
     * 删除所有角色权限映射
     * @param roleId
     */
    void deleteByRoleId(String roleId);
}
