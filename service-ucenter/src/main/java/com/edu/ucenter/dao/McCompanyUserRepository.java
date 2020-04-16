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

    /**
     * 根据用户名和状态查询
     * @param userId
     * @param status
     * @return
     */
    McCompanyUser findByUserIdAndStatus(String userId, String status);

    /**
     * 获取用户
     * @param userId
     * @param companyId
     * @return
     */
    McCompanyUser findByUserIdAndCompanyId(String userId, String companyId);

    void deleteByUserId(String userId);
}
