package com.edu.manage_course.controller;

import com.edu.api.course.CourseControllerApi;
import com.edu.framework.domain.course.CourseBase;
import com.edu.framework.domain.course.CourseMarket;
import com.edu.framework.domain.course.Teachplan;
import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.domain.course.request.CourseListRequest;
import com.edu.framework.model.response.CommonResponseResult;
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
    public CommonResponseResult findTeachplanList(@PathVariable String courseId) {
        return this.courseService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return this.courseService.addTeachplan(teachplan);
    }

    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(@PathVariable(required = false) int page,
                                              @PathVariable(required = false) int size,
                                              CourseListRequest courseListRequest) {
        return this.courseService.findCourseList(page, size, courseListRequest);
    }

    @Override
    @PostMapping("/coursebase/add")
    public ResponseResult addCourseBase(@RequestBody CourseBase courseBase) {
        return courseService.addCourseBase(courseBase);
    }

    @Override
    @GetMapping("/coursebase/get/{courseId}")
    public CommonResponseResult getCourseBaseById(@PathVariable String courseId) {
        return courseService.getCourseBaseById(courseId);
    }

    @Override
    @PutMapping("/coursebase/update/{id}")
    public ResponseResult updateCourseBase(@PathVariable String id, @RequestBody CourseBase courseBase) {
        return courseService.updateCourseBase(id, courseBase);
    }

    @Override
    @GetMapping("/coursemarket/get/{courseId}")
    public CommonResponseResult getCourseMarketById(@PathVariable String courseId) {
        return courseService.getCourseMarketById(courseId);
    }

    @Override
    @PutMapping("/coursemarket/update/{id}")
    public ResponseResult updateCourseMarket(@PathVariable String id, @RequestBody CourseMarket courseMarket) {
        return courseService.updateCourseMarket(id, courseMarket);
    }

    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam String courseId,@RequestParam String pic) {
        return courseService.saveCoursePic(courseId, pic);
    }

    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public CommonResponseResult findCoursePic(@PathVariable String courseId) {
        return courseService.findCoursepic(courseId);
    }

    @Override
    @DeleteMapping("/coursepic/delete")
    public ResponseResult deleteCoursePic(@RequestParam String courseId) {
        return courseService.deleteCoursePic(courseId);
    }

    @Override
    @GetMapping("/courseview/{courseId}")
    public CourseView courseview(@PathVariable String courseId) {
        return courseService.getCoruseView(courseId);
    }

    @Override
    @GetMapping("/preview/{courseId}")
    public CommonResponseResult preview(@PathVariable String courseId) {
        return courseService.preview(courseId);
    }
}
