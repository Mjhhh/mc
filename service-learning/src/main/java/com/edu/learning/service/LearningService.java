package com.edu.learning.service;

import com.edu.framework.domain.course.response.TeachplanMediaPub;
import com.edu.framework.domain.learning.GetMediaResult;
import com.edu.framework.domain.learning.response.LearningCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.learning.client.CourseSearchClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearningService {
    @Autowired
    CourseSearchClient courseSearchClient;

    //获取课程
    public GetMediaResult getMedia(String courseId, String teachplanId) {
        //校验学生的学习权限,是否资费等
        //调用搜索服务查询
        TeachplanMediaPub teachplanMediaPub = courseSearchClient.getmedia(teachplanId);
        if (teachplanMediaPub == null || StringUtils.isEmpty(teachplanMediaPub.getMediaUrl())) {
            //获取视频播放地址出错
            ExceptionCast.cast(LearningCode.LEARNING_URL_ERROR);
        }
        return new GetMediaResult(CommonCode.SUCCESS, teachplanMediaPub.getMediaUrl());
    }
}
