package com.edu.message.dao;

import com.edu.framework.domain.message.McUserMessage;
import com.edu.framework.domain.ucenter.McUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface McUserMessageRepository extends JpaRepository<McUserMessage, String> {

    List<McUserMessage> findByMessageId(String messageId);

    List<McUserMessage> findByUserId(String userId);

    McUserMessage findByUserIdAndMessageId(String userId, String messageId);

    List<McUserMessage> findByUserIdAndReadStatus(String userId, String status);

    void deleteByMessageId(String messageId);

}
