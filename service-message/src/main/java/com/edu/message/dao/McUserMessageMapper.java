package com.edu.message.dao;

import com.edu.framework.domain.message.McUserMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface McUserMessageMapper{
    List<String> selectMessageIdByUserId(String userId);

    List<McUserMessage> selectListByMessageIds(List<String> ids);
}
