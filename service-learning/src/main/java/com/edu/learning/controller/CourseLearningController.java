package com.edu.learning.controller;

import com.edu.api.learning.CourseLearningControllerApi;
import com.edu.framework.domain.learning.GetMediaResult;
import com.edu.learning.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learning/course")
public class CourseLearningController implements CourseLearningControllerApi {

    @Autowired
    LearningService learningService;


    @Override
    @GetMapping("/getmedia/{courseId}/{teachplanId}")
    public GetMediaResult getmedia(@PathVariable String courseId,@PathVariable String teachplanId) {
        return learningService.getMedia(courseId, teachplanId);
    }
}
