package com.edu.api.course;

import com.edu.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author mjh
 */
@Api(value = "课程分类管理", tags = "课程分类管理")
public interface CategoryControllerApi {

    @ApiOperation("查询分类")
    CategoryNode findList();
}
