package com.edu.api.search;

import com.edu.framework.domain.course.CoursePub;
import com.edu.framework.domain.course.response.TeachplanMediaPub;
import com.edu.framework.domain.search.CourseSearchParam;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * @author Admin
 */
@Api(value = "课程搜索", tags = "课程搜索管理")
public interface EsCourseControllerApi {

    @ApiOperation("课程综合搜索")
    QueryResponseResult list(int page, int size, CourseSearchParam courseSearchParam);

    @ApiOperation("根据id查询课程信息")
    CommonResponseResult getall(String id);

    @ApiOperation("根据课程计划查询媒资信息")
    TeachplanMediaPub getmedia(String teachplanId);

}
