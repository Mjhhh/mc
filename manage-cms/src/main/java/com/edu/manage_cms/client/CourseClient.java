package com.edu.manage_cms.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.course.CoursePub;
import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.domain.course.response.CoursePreResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = ServiceList.SERVER_MANAGE_COURSE)
public interface CourseClient {

    @GetMapping("/course/courseview/{courseId}")
    CourseView courseview(@PathVariable String courseId);

    @GetMapping("/course/courseview/prelist/{page}/{size}")
    CoursePreResult findCoursePreviewList(@PathVariable(required = false) int page,
                                          @PathVariable(required = false) int size);

    @GetMapping("/course/coursepub/list")
    List<CoursePub> findCoursePubByIds(@RequestParam List<String> ids);
}
