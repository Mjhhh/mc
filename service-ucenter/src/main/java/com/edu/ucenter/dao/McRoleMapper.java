package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface McRoleMapper{
    List<McRole> selectRoleByUserId(String userId);
}
