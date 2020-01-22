package com.edu.api.course;

import com.edu.framework.domain.course.Teachplan;
import com.edu.framework.domain.course.response.CourseCommonResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程管理接口", tags = "课程管理接口,提供课程的增，删，改，查")
public interface CourseControllerApi {

    @ApiOperation("课程计划查询")
    @ApiImplicitParam(name = "courseId", value = "课程id", paramType = "path")
    CourseCommonResult findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachplan(Teachplan teachplan);
}