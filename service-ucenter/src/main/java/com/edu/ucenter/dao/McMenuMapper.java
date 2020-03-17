package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Admin
 */
@Mapper
public interface McMenuMapper {
    /**
     * 获取用户权限
     * @param userid
     * @return
     */
    List<McMenu> selectPermissionByUserId(String userid);

    List<McMenu> selectPermissionByStatus(String status);

    List<McMenu> selectPermissionByStatusAndRoleId(@Param("status") String status,@Param("roleId") String roleId);
}
