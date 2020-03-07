package com.edu.search.controller;

import com.edu.api.search.EsCourseControllerApi;
import com.edu.framework.domain.search.CourseSearchParam;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Admin
 */
@RestController
@RequestMapping("/search/course")
public class EsCourseController implements EsCourseControllerApi {

    @Autowired
    EsCourseService esCourseService;

    @Override
    @GetMapping(value="/list/{page}/{size}")
    public QueryResponseResult list(@PathVariable int page, @PathVariable int size, CourseSearchParam courseSearchParam){
        return esCourseService.list(page, size, courseSearchParam);
    }
}
