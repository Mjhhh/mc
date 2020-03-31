package com.edu.manage_course.controller;

import com.edu.api.course.CourseControllerApi;
import com.edu.framework.domain.course.*;
import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.domain.course.request.CourseListRequest;
import com.edu.framework.domain.course.response.CoursePreResult;
import com.edu.framework.domain.course.response.CourseResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/coursebase/list")
    public CommonResponseResult findCourseListByIds(@RequestBody List<String> ids) {
        return this.courseService.findCourseListByIds(ids);
    }

    @Override
    @GetMapping("/courseview/prelist/{page}/{size}")
    public CoursePreResult findCoursePreviewList(@PathVariable(required = false) int page,
                                                 @PathVariable(required = false) int size) {
        return this.courseService.findCoursePreviewList(page, size);
    }

    @Override
    @PostMapping("/coursebase/add")
    public ResponseResult addCourseBase(@RequestBody CourseBase courseBase) {
        return courseService.addCourseBase(courseBase);
    }

    @Override
    @DeleteMapping("/coursebase/del/{courseId}")
    public ResponseResult delCourseBase(@PathVariable String courseId) {
        return courseService.delCourseBase(courseId);
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
    public ResponseResult addCoursePic(@RequestParam(value = "courseId") String courseId,
                                       @RequestParam(value = "pic") String pic) {
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
    @GetMapping("/coursepub/list")
    public List<CoursePub> findCoursePubByIds(@RequestParam List<String> ids) {
        return courseService.findCoursePubByIds(ids);
    }

    @Override
    @GetMapping("/courseview/{courseId}")
    public CourseView courseview(@PathVariable String courseId) {
        return courseService.getCoruseView(courseId);
    }

    @Override
    @PostMapping("/preview/{courseId}")
    public CourseResult preview(@PathVariable String courseId) {
        return courseService.preview(courseId);
    }

    @Override
    @PostMapping("/publish/{courseId}")
    public CourseResult publish(@PathVariable String courseId) {
        return courseService.publish(courseId);
    }

    @Override
    @PostMapping("/savemedia")
    public ResponseResult savemedia(@RequestBody TeachplanMedia teachplanMedia) {
        return courseService.savemedia(teachplanMedia);
    }

    @Override
    @GetMapping("/teachplan/get/{teachplanId}")
    public CommonResponseResult findTeachplanById(@PathVariable String teachplanId) {
        return courseService.findTeachplanById(teachplanId);
    }

    @Override
    @PostMapping("/teacher/add")
    public ResponseResult addCourseTeacher(@RequestBody CourseTeacher courseTeacher) {
        return courseService.addCourseTeacher(courseTeacher);
    }

    @Override
    @GetMapping("/teacher/get/{courseId}")
    public CommonResponseResult findCourseTeacher(@PathVariable String courseId) {
        return courseService.findcourseTeacher(courseId);
    }

    @Override
    @DeleteMapping("/teachplan/media/del/{mediaId}")
    public ResponseResult delTeachplanMedia(@PathVariable String mediaId) {
        return courseService.delTeachplanMedia(mediaId);
    }

    @Override
    @PostMapping("/courseevaluate/add")
    public ResponseResult addCourseEvaluate(@RequestBody CourseEvaluate courseEvaluate) {
        return courseService.addCourseEvaluate(courseEvaluate);
    }

    @Override
    @GetMapping("/courseevaluate/list/{courseId}")
    public CommonResponseResult findCourseEvaluateList(@PathVariable String courseId) {
        return courseService.findCourseEvaluateList(courseId);
    }

    @Override
    @DeleteMapping("/courseevaluate/list/{id}")
    public ResponseResult delCourseEvaluate(@PathVariable String id) {
        return courseService.delCourseEvaluate(id);
    }


}
