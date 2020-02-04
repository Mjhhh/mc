package com.edu.api.course;

import com.edu.framework.domain.course.Teachplan;
import com.edu.framework.domain.course.ext.CourseInfo;
import com.edu.framework.domain.course.request.CourseListRequest;
import com.edu.framework.domain.course.response.CourseCommonResult;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.engine.jdbc.Size;

@Api(value = "课程管理接口", tags = "课程管理接口,提供课程的增，删，改，查")
public interface CourseControllerApi {

    @ApiOperation("课程计划查询")
    @ApiImplicitParam(name = "courseId", value = "课程id", paramType = "path")
    CourseCommonResult findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("查询我的课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "page", paramType = "query"),
            @ApiImplicitParam(value = "条数", name = "size", paramType = "query"),
    })
    QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);
}