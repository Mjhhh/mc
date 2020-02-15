package com.edu.manage_course.dao;


import com.edu.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Admin
 */
public interface CoursePicRepository extends JpaRepository<CoursePic, String> {
    /**
     * 删除成功返回1否则返回0
     * @param courseid 课程编号
     * @return 是否成功
     */
    long deleteByCourseid(String courseid);
}
