package com.edu.manage_course.dao;


import com.edu.framework.domain.course.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseMarketRepository extends JpaRepository<CourseMarket,String> {
}
