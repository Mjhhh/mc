package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McUserRepository extends JpaRepository<McUser, String> {
    /**
     * 根绝用户名查询用户信息
     * @param username
     * @return
     */
    McUser findByUsername(String username);
}
