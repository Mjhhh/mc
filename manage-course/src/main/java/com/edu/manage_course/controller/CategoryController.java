package com.edu.manage_course.controller;

import com.edu.api.course.CategoryControllerApi;
import com.edu.framework.domain.course.Category;
import com.edu.framework.domain.course.ext.CategoryNode;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Override
    @GetMapping("/findByIds")
    public List<Category> findByIds(@RequestParam List<String> ids) {
        return categoryService.findByIds(ids);
    }

    @Override
    @PostMapping("/addCourseCategory")
    public ResponseResult addCourseCategory(@RequestBody Category category) {
        return categoryService.addCourseCategory(category);
    }

    @Override
    @DeleteMapping("/delCourseCategory/{id}")
    public ResponseResult delCourseCategory(@PathVariable String id) {
        return categoryService.delCourseCategory(id);
    }
}
