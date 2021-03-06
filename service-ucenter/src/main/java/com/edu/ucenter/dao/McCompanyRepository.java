package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McCompany;
import com.edu.framework.domain.ucenter.McMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface McCompanyRepository extends JpaRepository<McCompany, String> {
    McCompany findByUserIdAndStatus(String userId, String status);


    McCompany findByIdAndStatus(String id, String status);


    McCompany findByUserId(String userId);
}
