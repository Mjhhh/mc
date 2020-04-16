package com.edu.learning.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.framework.domain.course.CourseBase;
import com.edu.framework.domain.course.CourseMarket;
import com.edu.framework.domain.course.response.TeachplanMediaPub;
import com.edu.framework.domain.learning.GetMediaResult;
import com.edu.framework.domain.learning.McLearningCourse;
import com.edu.framework.domain.learning.response.LearningCode;
import com.edu.framework.domain.order.McOrders;
import com.edu.framework.domain.order.McOrdersPay;
import com.edu.framework.domain.task.McTask;
import com.edu.framework.domain.task.McTaskHis;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.utils.McOauth2Util;
import com.edu.learning.client.CourseClient;
import com.edu.learning.client.CourseSearchClient;
import com.edu.learning.client.OrderClient;
import com.edu.learning.dao.McLearningCourseRepository;
import com.edu.learning.dao.McTaskHisRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LearningService {
    @Autowired
    CourseSearchClient courseSearchClient;

    @Autowired
    McLearningCourseRepository mcLearningCourseRepository;

    @Autowired
    McTaskHisRepository mcTaskHisRepository;

    @Autowired
    CourseClient courseClient;

    @Autowired
    OrderClient orderClient;

    /**
     * 获取用户id
     *
     * @return
     */
    private String getUserId() {
        HttpServletRequest request = this.getRequest();
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (StringUtils.isBlank(userJwt.getId())) {
            ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        }
        return userJwt.getId();
    }

    /**
     * 获取HttpServletRequest
     * @return
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private McLearningCourse getMcLearningCourse(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Optional<McLearningCourse> optional = mcLearningCourseRepository.findById(id);
        return optional.orElse(null);
    }

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
     * @param mcOrders
     * @param mcTask
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addcourse(McOrders mcOrders, McTask mcTask) {
        if (StringUtils.isEmpty(mcOrders.getCourseId())) {
            ExceptionCast.cast(LearningCode.LEARNING_GET_MEDIA_ERROR);
        }
        if (StringUtils.isEmpty(mcOrders.getUserId())) {
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_USERISNULl);
        }
        if (mcTask == null || StringUtils.isEmpty(mcTask.getId())) {
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_TASKISNULL);
        }
        String userId = mcOrders.getUserId();
        String courseId = mcOrders.getCourseId();
        //查询用户学习教程
        McLearningCourse mcLearningCourse = mcLearningCourseRepository.findByUserIdAndCourseId(userId, courseId);
        if (mcLearningCourse == null) {
            //添加新的选课记录
            mcLearningCourse = new McLearningCourse();
            mcLearningCourse.setUserId(userId);
            mcLearningCourse.setCourseId(courseId);
        }
        mcLearningCourse.setStartTime(mcOrders.getStartTime());
        mcLearningCourse.setEndTime(mcOrders.getEndTime());
        mcLearningCourse.setStatus("501001");
        mcLearningCourse.setValid(mcOrders.getValid());
        mcLearningCourse.setCharge("203001");
        mcLearningCourse.setValid(mcOrders.getValid());
        mcLearningCourse.setPrice(mcOrders.getPrice());
        mcLearningCourseRepository.save(mcLearningCourse);

        //查询历史任务
        Optional<McTaskHis> optional = mcTaskHisRepository.findById(mcTask.getId());
        if (!optional.isPresent()) {
            //添加历史任务
            McTaskHis mcTaskHis = new McTaskHis();
            BeanUtils.copyProperties(mcTask, mcTaskHis);
            mcTaskHis.setStatus("105002");
            mcTaskHisRepository.save(mcTaskHis);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加课程学习
     * @param courseId
     * @return
     */
    public ResponseResult addCourseStudy(String courseId) {
        CommonResponseResult commonResponseResult = courseClient.getCourseMarketById(courseId);
        if (commonResponseResult.getData() == null) {
            ExceptionCast.cast(LearningCode.COURSE_IS_NOT_EXISTS);
        }
        Object data = commonResponseResult.getData();
        CourseMarket courseMarket = JSON.parseObject(JSONObject.toJSONString(data), CourseMarket.class);
        HttpServletRequest request = this.getRequest();
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (userJwt == null || StringUtils.isBlank(userJwt.getId())) {
            ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        }
        String userId = userJwt.getId();
        McLearningCourse mcLearningCourse = mcLearningCourseRepository.findByUserIdAndCourseId(courseId, userId);
        if (mcLearningCourse == null) {
            mcLearningCourse = new McLearningCourse();
        }
        //收费
        if (StringUtils.equals(courseMarket.getCharge(), "203002")) {
            //检查用户是否已经付款
            McOrders mcOrders = orderClient.findOrdersByUserAndCourse(userId, courseId);
            if (mcOrders == null) {
                ExceptionCast.cast(LearningCode.ORDER_IS_NOT_EXISTS);
            }
            if (!StringUtils.equals(mcOrders.getStatus(), "401002")) {
                ExceptionCast.cast(LearningCode.ORDER_IS_NOT_PAY);
            }
            mcLearningCourse.setPrice(courseMarket.getPrice());
            mcLearningCourse.setStartTime(new Date());
            mcLearningCourse.setEndTime(courseMarket.getExpires());
        }
        mcLearningCourse.setCourseId(courseId);
        mcLearningCourse.setUserId(userId);
        mcLearningCourse.setCharge(courseMarket.getCharge());
        mcLearningCourse.setValid(courseMarket.getValid());
        //指定时间段
        if (StringUtils.equals(courseMarket.getValid(), "204002")) {
            mcLearningCourse.setStartTime(new Date());
            //获取截止日期
            mcLearningCourse.setEndTime(courseMarket.getExpires());
        }
        //501001-正常 501002-结束 501003-取消
        mcLearningCourse.setStatus("501001");
        mcLearningCourseRepository.save(mcLearningCourse);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取课程学习列表
     * @param userId
     * @return
     */
    public CommonResponseResult findLearningCourse(String userId) {
        if (StringUtils.isBlank(userId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        List<McLearningCourse> mcLearningCourseList = mcLearningCourseRepository.findByUserId(userId);
        return new CommonResponseResult(CommonCode.SUCCESS, mcLearningCourseList);
    }

    /**
     * 查询学员选课状态
     * @param courseId
     * @return
     */
    public CommonResponseResult findLearningStatus(String courseId) {
        String userId = this.getUserId();
        McLearningCourse mcLearningCourse = mcLearningCourseRepository.findByUserIdAndCourseId(userId, courseId);
        return new CommonResponseResult(CommonCode.SUCCESS, mcLearningCourse);
    }
}
