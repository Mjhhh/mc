package com.edu.api.learning;

import com.edu.framework.domain.learning.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "录播课程学习管理", tags = "录播课程学习管理")
public interface CourseLearningControllerApi {

    @ApiOperation("获取课程学习地址")
    GetMediaResult getmedia(String courseId, String teachplanId);
}
