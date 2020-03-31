package com.edu.api.learning;

import com.edu.framework.domain.learning.GetMediaResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "我的课程管理", tags = "我的课程管理")
public interface CourseLearningControllerApi {

    @ApiOperation("获取课程学习地址")
    GetMediaResult getmedia(String courseId, String teachplanId);

    @ApiOperation("添加课程学习")
    ResponseResult addCourseStudy(String courseId);

    @ApiOperation("获取课程学习列表")
    CommonResponseResult findLearningCourse(String userId);

    @ApiOperation("获取学员选课状态")
    CommonResponseResult findLearningStatus(String courseId);
}
