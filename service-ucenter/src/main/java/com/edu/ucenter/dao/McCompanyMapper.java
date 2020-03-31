package com.edu.ucenter.dao;

import com.edu.framework.domain.ucenter.McUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface McCompanyMapper {
    List<McUser> selectMcUserByCompanyId(String companyId);
}
