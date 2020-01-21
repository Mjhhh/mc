package com.edu.manage_course.service;

import com.edu.framework.domain.course.ext.TeachplanNode;
import com.edu.framework.domain.course.response.CourseCommonResult;
import com.edu.framework.model.response.CommonCode;
import com.edu.manage_course.dao.TeachplanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    TeachplanMapper teachplanMapper;

    /**
     * 查询课程计划
     * @param courseId 课程id
     * @return 课程计划列表
     */
    public CourseCommonResult findTeachplanList(String courseId) {
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return new CourseCommonResult(CommonCode.SUCCESS, teachplanNode);
    }
}
