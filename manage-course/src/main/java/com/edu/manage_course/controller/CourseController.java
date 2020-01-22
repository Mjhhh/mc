package com.edu.manage_course.controller;

import com.edu.api.course.CourseControllerApi;
import com.edu.framework.domain.course.Teachplan;
import com.edu.framework.domain.course.response.CourseCommonResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {

    @Autowired
    CourseService courseService;

    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public CourseCommonResult findTeachplanList(@PathVariable String courseId) {
        return this.courseService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return this.courseService.addTeachplan(teachplan);
    }
}
