package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Admin
 */
@Mapper
public interface McMenuMapper {
    /**
     * 获取用户权限
     * @param userid
     * @return
     */
    List<McMenu> selectPermissionByUserId(String userid);
}
