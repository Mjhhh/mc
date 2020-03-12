package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McCompanyUserRepository extends JpaRepository<McCompanyUser, String> {
    /**
     * 根据用户id查询用户所属企业
     * @param userId
     * @return
     */
    McCompanyUser findByUserId(String userId);
}
