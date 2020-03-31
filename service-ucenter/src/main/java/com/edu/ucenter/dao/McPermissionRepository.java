package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface McPermissionRepository extends JpaRepository<McPermission, String> {
    List<McPermission> findByMenuId(String menuId);

    void deleteByRoleId(String roleId);

    void deleteByMenuId(String menuId);
}
