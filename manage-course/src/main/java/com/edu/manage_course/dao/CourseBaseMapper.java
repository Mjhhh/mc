package com.edu.manage_course.dao;

import com.edu.framework.domain.course.CourseBase;
import com.edu.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseBaseMapper {
    List<CourseBase> selectListByIds(List<String> ids);
}
