package com.edu.api.search;

import com.edu.framework.domain.search.CourseSearchParam;
import com.edu.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Admin
 */
@Api(value = "课程搜索", tags = "课程搜索管理")
public interface EsCourseControllerApi {

    @ApiOperation("课程综合搜索")
    QueryResponseResult list(int page, int size, CourseSearchParam courseSearchParam);

}
