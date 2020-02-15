package com.edu.manage_course.dao;

import com.edu.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CategoryMapper {
   /**
    * 查询分类
    * @return
    */
   CategoryNode selectList();
}
