package com.edu.manage_course.dao;

import com.edu.framework.domain.course.response.TeachplanMediaPub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeachplanMediaPubRepository extends JpaRepository<TeachplanMediaPub, String> {
    /**
     * 根据课程id删除课程媒资信息
     * @param courseId
     * @return
     */
    void deleteByCourseId(String courseId);

    List<TeachplanMediaPub> findByCourseId(String courseId);
}
