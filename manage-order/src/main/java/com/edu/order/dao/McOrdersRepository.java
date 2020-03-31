package com.edu.order.dao;

import com.edu.framework.domain.order.McOrders;
import com.edu.framework.domain.task.McTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface McOrdersRepository extends JpaRepository<McOrders, String> {

    McOrders findByUserIdAndCourseId(String userId, String courseId);

    List<McOrders> findByUserId(String userId);

}
