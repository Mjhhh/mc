package com.edu.api.course;

import com.edu.framework.domain.course.CourseBase;
import com.edu.framework.domain.course.CourseMarket;
import com.edu.framework.domain.course.Teachplan;
import com.edu.framework.domain.course.TeachplanMedia;
import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.domain.course.request.CourseListRequest;
import com.edu.framework.domain.course.response.CourseResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author Admin
 */
@Api(value = "课程管理接口", tags = "课程管理接口,提供课程的增，删，改，查")
public interface CourseControllerApi {

    @ApiOperation("课程计划查询")
    @ApiImplicitParam(name = "courseId", value = "课程id", paramType = "path")
    CommonResponseResult findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("查询我的课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "page", paramType = "path", dataType = "int"),
            @ApiImplicitParam(value = "条数", name = "size", paramType = "path", dataType = "int"),
    })
    QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation("添加课程基础信息")
    ResponseResult addCourseBase(CourseBase courseBase);

    @ApiOperation("获取课程基本信息")
    @ApiImplicitParam(value = "课程ID", name = "courseId", paramType = "path", dataType = "String")
    CommonResponseResult getCourseBaseById(String courseId);

    @ApiOperation("更新课程基础信息")
    @ApiImplicitParam(value = "课程ID", name = "id", paramType = "path", dataType = "String")
    ResponseResult updateCourseBase(String id, CourseBase courseBase);

    @ApiOperation("获取课程营销信息")
    @ApiImplicitParam(value = "课程ID", name = "courseId", paramType = "path", dataType = "String")
    CommonResponseResult getCourseMarketById(String courseId);

    @ApiOperation("更新课程营销信息")
    @ApiImplicitParam(value = "课程ID", name = "id", paramType = "path", dataType = "String")
    ResponseResult updateCourseMarket(String id, CourseMarket courseMarket);

    @ApiOperation("添加课程图片")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "课程编号", name = "courseId"),
            @ApiImplicitParam(value = "图片地址", name = "pic"),
    })
    ResponseResult addCoursePic(String courseId, String pic);

    @ApiOperation("获取课程基础信息")
    @ApiImplicitParam(value = "课程编号", name = "courseId", paramType = "path", dataType = "String")
    CommonResponseResult findCoursePic(String courseId);

    @ApiOperation("删除课程图片")
    ResponseResult deleteCoursePic(String courseId);

    @ApiOperation("课程视图查询")
    @ApiImplicitParam(value = "课程编号", name = "courseId", paramType = "path", dataType = "String")
    CourseView courseview(String courseId);

    @ApiOperation("预览课程")
    @ApiImplicitParam(value = "课程编号", name = "courseId", paramType = "path", dataType = "String")
    CourseResult preview(String courseId);

    @ApiOperation("发布课程")
    @ApiImplicitParam(value = "课程编号", name = "courseId", paramType = "path", dataType = "String")
    CourseResult publish(String courseId);

    @ApiOperation("保存媒资信息")
    ResponseResult savemedia(TeachplanMedia teachplanMedia);
}