package com.edu.message.dao;

import com.edu.framework.domain.message.McChatMsg;
import com.edu.framework.domain.message.McMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface McChatMsgRepository extends JpaRepository<McChatMsg, String> {
    List<McChatMsg> findByAcceptUserIdAndSignFlag(String acceptUserId, Integer SignFlag);


}
