package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface McUserRoleRepository extends JpaRepository<McUserRole, String> {

    List<McUserRole> findByUserId(String userId);

    void deleteByRoleId(String roleId);

    void deleteByUserId(String userId);
}
