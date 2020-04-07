package com.edu.manage_course.dao;


import com.edu.framework.domain.course.CourseAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseAnswerRepository extends JpaRepository<CourseAnswer,String> {

    List<CourseAnswer> findByQuestionId(String questionId);
}
