package com.edu.manage_course.dao;

import com.edu.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeachplanRepository extends JpaRepository<Teachplan, String> {
    /**
     * 定义方法根据课程id和父结点id查询出结点列表，可以使用此方法实现查询根结点
     * @param courseId 课程id
     * @param parentId 上级节点id
     * @return 课程计划列表
     */
    List<Teachplan> findByCourseidAndParentid(String courseId, String parentId);
}
