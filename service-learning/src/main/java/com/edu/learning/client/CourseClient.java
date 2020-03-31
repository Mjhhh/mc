package com.edu.learning.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.domain.course.response.CoursePreResult;
import com.edu.framework.model.response.CommonResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = ServiceList.SERVER_MANAGE_COURSE)
public interface CourseClient {

    @GetMapping("/course/courseview/{courseId}")
    CourseView courseview(@PathVariable String courseId);

    @GetMapping("/course/coursebase/get/{courseId}")
    CommonResponseResult getCourseBaseById(@PathVariable String courseId);

    @GetMapping("/course/coursemarket/get/{courseId}")
    CommonResponseResult getCourseMarketById(@PathVariable String courseId);
}
