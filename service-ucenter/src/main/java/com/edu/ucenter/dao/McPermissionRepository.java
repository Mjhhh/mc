package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McPermissionRepository extends JpaRepository<McPermission, String> {
    void deleteByRoleId(String roleId);
}
