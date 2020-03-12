package com.edu.learning.service;

import com.edu.framework.domain.course.response.TeachplanMediaPub;
import com.edu.framework.domain.learning.GetMediaResult;
import com.edu.framework.domain.learning.McLearningCourse;
import com.edu.framework.domain.learning.response.LearningCode;
import com.edu.framework.domain.task.McTask;
import com.edu.framework.domain.task.McTaskHis;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.ResponseResult;
import com.edu.learning.client.CourseSearchClient;
import com.edu.learning.dao.McLearningCourseRepository;
import com.edu.learning.dao.McTaskHisRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class LearningService {
    @Autowired
    CourseSearchClient courseSearchClient;

    @Autowired
    McLearningCourseRepository mcLearningCourseRepository;

    @Autowired
    McTaskHisRepository mcTaskHisRepository;

    /**
     * 获取课程
     *
     * @param courseId
     * @param teachplanId
     * @return
     */
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

    /**
     * 添加选课
     *
     * @param userId
     * @param courseId
     * @param valid
     * @param startTime
     * @param endTime
     * @param mcTask
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addcourse(String userId, String courseId, String valid, Date startTime, Date endTime, McTask mcTask) {
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(LearningCode.LEARNING_GET_MEDIA_ERROR);
        }
        if (StringUtils.isEmpty(userId)) {
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_USERISNULl);
        }
        if (mcTask == null || StringUtils.isEmpty(mcTask.getId())) {
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_TASKISNULL);
        }
        //查询用户学习教程
        McLearningCourse mcLearningCourse = mcLearningCourseRepository.findByUserIdAndCourseId(userId, courseId);
        if (mcLearningCourse == null) {
            //添加新的选课记录
            mcLearningCourse = new McLearningCourse();
            mcLearningCourse.setUserId(userId);
            mcLearningCourse.setCourseId(courseId);
        }
        mcLearningCourse.setStartTime(startTime);
        mcLearningCourse.setEndTime(endTime);
        mcLearningCourse.setStatus("501001");
        mcLearningCourseRepository.save(mcLearningCourse);

        //查询历史任务
        Optional<McTaskHis> optional = mcTaskHisRepository.findById(mcTask.getId());
        if (!optional.isPresent()) {
            //添加历史任务
            McTaskHis mcTaskHis = new McTaskHis();
            BeanUtils.copyProperties(mcTask, mcTaskHis);
            mcTaskHisRepository.save(mcTaskHis);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

}
