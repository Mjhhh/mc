package com.edu.message.dao;

import com.edu.framework.domain.message.McMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface McMessageMapper {
    List<McMessage> selectListByIds(List<String> ids);
}
