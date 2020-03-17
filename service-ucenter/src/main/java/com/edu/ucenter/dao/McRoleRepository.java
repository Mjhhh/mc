package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McRole;
import com.edu.framework.domain.ucenter.McUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McRoleRepository extends JpaRepository<McRole, String> {
    /**
     * 根绝用户名查询用户信息
     * @param roleName
     * @return
     */
    McRole findByRoleName(String roleName);
}
