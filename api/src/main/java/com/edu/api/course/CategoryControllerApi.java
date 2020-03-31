package com.edu.api.course;

import com.edu.framework.domain.course.Category;
import com.edu.framework.domain.course.ext.CategoryNode;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author mjh
 */
@Api(value = "课程分类管理", tags = "课程分类管理")
public interface CategoryControllerApi {

    @ApiOperation("查询分类")
    CategoryNode list();

    @ApiOperation("根据id查询分类列表")
    List<Category> findByIds(List<String> ids);

    @ApiOperation("添加课程分类")
    ResponseResult addCourseCategory(Category category);

    @ApiOperation("删除课程分类")
    ResponseResult delCourseCategory(String id);
}
