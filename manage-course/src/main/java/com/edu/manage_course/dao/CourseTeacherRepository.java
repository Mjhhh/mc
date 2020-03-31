package com.edu.manage_course.dao;

import com.edu.framework.domain.course.CourseTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTeacherRepository extends JpaRepository<CourseTeacher, String> {
}
