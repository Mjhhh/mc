package com.edu.order.dao;

import com.edu.framework.domain.order.McOrdersPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McOrdersPayRepository extends JpaRepository<McOrdersPay, String> {

    McOrdersPay findByOrderId(String orderId);

}
