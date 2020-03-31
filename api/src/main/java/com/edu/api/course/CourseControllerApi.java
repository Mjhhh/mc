package com.edu.api.course;

import com.edu.framework.domain.course.*;
import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.domain.course.request.CourseListRequest;
import com.edu.framework.domain.course.response.CoursePreResult;
import com.edu.framework.domain.course.response.CourseResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author Admin
 */
@Api(value = "课程管理接口", tags = "课程管理接口,提供课程的增，删，改，查")
public interface CourseControllerApi {

    @ApiOperation("课程计划查询")
    CommonResponseResult findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("查询我的课程列表")
    QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation("根据课程id查找课程列表")
    CommonResponseResult findCourseListByIds(List<String> ids);

    @ApiOperation("查询课程封面预览信息")
    CoursePreResult findCoursePreviewList(int page, int size);

    @ApiOperation("添加课程基础信息")
    ResponseResult addCourseBase(CourseBase courseBase);

    @ApiOperation("删除课程基本信息")
    ResponseResult delCourseBase(String courseId);

    @ApiOperation("获取课程基本信息")
    CommonResponseResult getCourseBaseById(String courseId);

    @ApiOperation("更新课程基础信息")
    ResponseResult updateCourseBase(String id, CourseBase courseBase);

    @ApiOperation("获取课程营销信息")
    CommonResponseResult getCourseMarketById(String courseId);

    @ApiOperation("更新课程营销信息")
    ResponseResult updateCourseMarket(String id, CourseMarket courseMarket);

    @ApiOperation("添加课程图片")
    ResponseResult addCoursePic(String courseId, String pic);

    @ApiOperation("获取课程图片信息")
    CommonResponseResult findCoursePic(String courseId);

    @ApiOperation("删除课程图片")
    ResponseResult deleteCoursePic(String courseId);

    @ApiOperation("根据id查找课程索引")
    List<CoursePub> findCoursePubByIds(List<String> ids);

    @ApiOperation("课程模型数据查询")
    CourseView courseview(String courseId);

    @ApiOperation("预览课程")
    CourseResult preview(String courseId);

    @ApiOperation("发布课程")
    CourseResult publish(String courseId);

    @ApiOperation("保存媒资信息")
    ResponseResult savemedia(TeachplanMedia teachplanMedia);

    @ApiOperation("根据id获取课程计划信息")
    CommonResponseResult findTeachplanById(String teachplanId);

    @ApiOperation("添加课程讲师")
    ResponseResult addCourseTeacher(CourseTeacher courseTeacher);

    @ApiOperation("查询课程讲师")
    CommonResponseResult findCourseTeacher(String courseId);

    @ApiOperation("删除课程视频映射记录")
    ResponseResult delTeachplanMedia(String mediaId);

    @ApiOperation("添加课程评论")
    ResponseResult addCourseEvaluate(CourseEvaluate courseEvaluate);

    @ApiOperation("获取课程评论列表")
    CommonResponseResult findCourseEvaluateList(String courseId);

    @ApiOperation("删除课程评论")
    ResponseResult delCourseEvaluate(String id);
}