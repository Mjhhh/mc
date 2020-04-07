package com.edu.manage_course.dao;


import com.edu.framework.domain.course.CourseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseQuestionRepository extends JpaRepository<CourseQuestion,String> {

    @Query("select q from CourseQuestion q where q.courseId = :courseId order by q.createTime desc")
    List<CourseQuestion> selectByCourseId(@Param("courseId") String courseId);
}
