package com.edu.manage_course.dao;


import com.edu.framework.domain.course.CourseEvaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseEvaluateRepository extends JpaRepository<CourseEvaluate,String> {

    @Query("select e from CourseEvaluate e where e.courseId = :courseId order by e.createTime desc")
    List<CourseEvaluate> selectByCourseId(@Param("courseId") String courseId);
}
