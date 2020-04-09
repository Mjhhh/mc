package com.edu.manage_course.dao;

import com.edu.framework.domain.course.CoursePub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursePubRepository extends JpaRepository<CoursePub, String> {
}
