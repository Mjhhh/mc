package com.edu.manage_course.service;

import com.edu.framework.domain.course.ext.CategoryNode;
import com.edu.manage_course.dao.CategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    /**
     * 查询分类
     * @return 分类树
     */
    public CategoryNode findList() {
        return categoryMapper.selectList();
    }
}
