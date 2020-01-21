package com.edu.manage_course.dao;

import com.edu.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeachplanMapper {

    /**
     * 根据课程id查询课程计划列表
     * @param courseId 课程id
     * @return 课程计划列表
     */
    TeachplanNode selectList(String courseId);

}
