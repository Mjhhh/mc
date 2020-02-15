package com.edu.manage_course.controller;

import com.edu.api.course.CategoryControllerApi;
import com.edu.framework.domain.course.ext.CategoryNode;
import com.edu.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryControllerApi {

    @Autowired
    CategoryService categoryService;

    @Override
    @GetMapping("/list")
    public CategoryNode list() {
        return categoryService.findList();
    }
}
