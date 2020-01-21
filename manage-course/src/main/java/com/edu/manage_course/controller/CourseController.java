package com.edu.manage_course.controller;

import com.edu.api.course.CourseControllerApi;
import com.edu.framework.domain.course.response.CourseCommonResult;
import com.edu.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {

    @Autowired
    CourseService courseService;

    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public CourseCommonResult findTeachplanList(String courseId) {
        return courseService.findTeachplanList(courseId);
    }
}
