package com.edu.learning.dao;

import com.edu.framework.domain.learning.McLearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Admin
 */
public interface McLearningCourseRepository extends JpaRepository<McLearningCourse,String> {
    /**
     * 根据用户id和课程id查询
     * @param userId
     * @param courseId
     * @return
     */
    McLearningCourse findByUserIdAndCourseId(String userId, String courseId);

    /**
     * 获取课程学习列表
     * @param userId
     * @return
     */
    List<McLearningCourse> findByUserId(String userId);
}
