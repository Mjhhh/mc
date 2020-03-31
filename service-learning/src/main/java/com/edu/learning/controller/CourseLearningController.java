package com.edu.learning.controller;

import com.edu.api.learning.CourseLearningControllerApi;
import com.edu.framework.domain.learning.GetMediaResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.learning.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learning")
public class CourseLearningController implements CourseLearningControllerApi {

    @Autowired
    LearningService learningService;

    @Override
    @GetMapping("/course/getmedia/{courseId}/{teachplanId}")
    public GetMediaResult getmedia(@PathVariable String courseId,@PathVariable String teachplanId) {
        return learningService.getMedia(courseId, teachplanId);
    }

    @Override
    @PostMapping("/choosecourse/addcoursestudy/{courseId}")
    public ResponseResult addCourseStudy(@PathVariable String courseId) {
        return learningService.addCourseStudy(courseId);
    }

    @Override
    @GetMapping("/choosecourse/list/{userId}")
    public CommonResponseResult findLearningCourse(@PathVariable String userId) {
        return learningService.findLearningCourse(userId);
    }

    @Override
    @GetMapping("/choosecourse/learnstatus/{courseId}")
    public CommonResponseResult findLearningStatus(@PathVariable String courseId) {
        return learningService.findLearningStatus(courseId);
    }


}
