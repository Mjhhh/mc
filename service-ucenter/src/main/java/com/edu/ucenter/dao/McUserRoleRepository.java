package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McUserRoleRepository extends JpaRepository<McUserRole, String> {
    void deleteByRoleId(String roleId);
}
