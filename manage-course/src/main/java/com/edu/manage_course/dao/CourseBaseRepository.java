package com.edu.manage_course.dao;


import com.edu.framework.domain.course.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseBaseRepository extends JpaRepository<CourseBase,String> {
    List<CourseBase> findByCompanyId(String companyId);
}
