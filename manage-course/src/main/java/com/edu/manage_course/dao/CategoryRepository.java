package com.edu.manage_course.dao;


import com.edu.framework.domain.course.Category;
import com.edu.framework.domain.course.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {
    /**
     * 根据父id查找分类信息
     * @param parentId
     * @return
     */
    List<Category> findByParentid(String parentId);

    /**
     * 根据parentId删除分类信息
     * @param parentId
     */
    void deleteByParentid(String parentId);
}
