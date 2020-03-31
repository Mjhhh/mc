package com.edu.manage_course.dao;


import com.edu.framework.domain.course.CourseEvaluate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseEvaluateRepository extends JpaRepository<CourseEvaluate,String> {

    List<CourseEvaluate> findByCourseId(String courseId);
}
