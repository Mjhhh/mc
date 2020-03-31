package com.edu.auth.dao;

import com.edu.framework.domain.ucenter.McUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McUserRepository extends JpaRepository<McUser, String> {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    McUser findByUsername(String username);

    /**
     * 根据用户名和昵称查找用户
     * @param username
     * @param name
     * @return
     */
    McUser findByUsernameAndName(String username, String name);

    /**
     * 根据手机号码获取用户
     * @param phone
     * @return
     */
    McUser findByPhone(String phone);
}
