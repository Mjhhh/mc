package com.edu.manage_cms.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.course.Category;
import com.edu.framework.domain.course.ext.CategoryNode;
import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = ServiceList.SERVER_MANAGE_COURSE)
public interface CategoryClient {

    /**
     * 获取分类列表
     * @return
     */
    @GetMapping("/category/list")
    CategoryNode list();

    /**
     * 通过id获取分类信息
     * @param ids
     * @return
     */
    @GetMapping("/category/findByIds")
    List<Category> findByIds(@RequestParam List<String> ids);

    /**
     * 添加课程分类信息
     * @param category
     * @return
     */
    @PostMapping("/category/addCourseCategory")
    ResponseResult addCourseCategory(@RequestBody Category category);

    /**
     * 删除课程分类信息
     * @param id
     * @return
     */
    @DeleteMapping("/category/delCourseCategory/{id}")
    ResponseResult delCourseCategory(@PathVariable String id);
}
