package com.edu.manage_course.dao;

import com.edu.framework.domain.course.TeachplanMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeachplanMediaRepository extends JpaRepository<TeachplanMedia, String> {

    /**
     * 根据课程id查询课程信息
     * @param courseId
     * @return
     */
    List<TeachplanMedia> findByCourseId(String courseId);
}
