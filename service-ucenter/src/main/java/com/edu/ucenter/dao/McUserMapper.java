package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.request.QueryMcUserRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Admin
 */
@Mapper
public interface McUserMapper {

    /**
     * 获取用户列表
     * @return
     */
    List<McUser> findList(QueryMcUserRequest request);
}
