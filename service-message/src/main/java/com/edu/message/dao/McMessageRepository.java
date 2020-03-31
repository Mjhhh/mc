package com.edu.message.dao;

import com.edu.framework.domain.message.McMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McMessageRepository extends JpaRepository<McMessage, String> {
}
