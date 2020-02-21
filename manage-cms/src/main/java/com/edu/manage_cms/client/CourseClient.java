package com.edu.manage_cms.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.course.ext.CourseView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = ServiceList.SERVER_MANAGE_COURSE)
public interface CourseClient {
    @GetMapping("/course/courseview/{courseId}")
    CourseView courseview(@PathVariable String courseId);
}
