package com.edu.manage_course.controller;

import com.edu.api.course.CourseControllerApi;
import com.edu.framework.domain.course.Teachplan;
import com.edu.framework.domain.course.request.CourseListRequest;
import com.edu.framework.domain.course.response.CourseCommonResult;
import com.edu.framework.model.response.QueryResponseResult;
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

    @Override
    @PostMapping("/list")
    public QueryResponseResult findCourseList(@RequestParam(required = false, defaultValue = "1") int page,
                                              @RequestParam(required = false, defaultValue = "10") int size,
                                              @RequestBody CourseListRequest courseListRequest) {
        return this.courseService.findCourseList(page, size, courseListRequest);
    }
}
