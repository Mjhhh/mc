package com.edu.manage_course.dao;

import com.edu.framework.domain.course.CoursePub;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoursePubMapper {
    List<CoursePub> selectListByIds(List<String> ids);

}
