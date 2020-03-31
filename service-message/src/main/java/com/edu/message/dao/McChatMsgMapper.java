package com.edu.message.dao;

import com.edu.framework.domain.message.McChatMsg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface McChatMsgMapper {

    /**
     * 查询指定用户聊天列表
     */
    List<McChatMsg> selectListByUserId(String userId);

    List<McChatMsg> selectListByUserIdAndUserName(String userId, String username);
}
