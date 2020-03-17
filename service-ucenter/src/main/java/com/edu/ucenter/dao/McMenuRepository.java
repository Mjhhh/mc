package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McMenu;
import com.edu.framework.domain.ucenter.McRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface McMenuRepository extends JpaRepository<McMenu, String> {
    /**
     * 查找未被禁用的权限列表
     * @param status
     * @return
     */
    List<McMenu> findByStatus(String status);
}
