package com.edu.manage_course.dao;

import com.edu.framework.domain.course.CourseBase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
   /**
    * 根据公司ID查询课程列表
    * @param companyId 公司ID
    * @return 课程列表
    */
   List<CourseBase> findByCompanyId(String companyId);
}
