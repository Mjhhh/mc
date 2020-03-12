package com.edu.search.controller;

import com.edu.api.search.EsCourseControllerApi;
import com.edu.framework.domain.course.CoursePub;
import com.edu.framework.domain.course.response.TeachplanMediaPub;
import com.edu.framework.domain.search.CourseSearchParam;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.model.response.QueryResult;
import com.edu.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Admin
 */
@RestController
@RequestMapping("/search/course")
public class EsCourseController implements EsCourseControllerApi {

    @Autowired
    EsCourseService esCourseService;

    @Override
    @GetMapping(value = "/list/{page}/{size}")
    public QueryResponseResult list(@PathVariable int page, @PathVariable int size, CourseSearchParam courseSearchParam) {
        return esCourseService.list(page, size, courseSearchParam);
    }

    @Override
    @GetMapping(value = "/getall/{id}")
    public CommonResponseResult getall(@PathVariable("id") String id) {
        return esCourseService.getall(id);
    }

    @Override
    @GetMapping(value = "/getmedia/{teachplanId}")
    public TeachplanMediaPub getmedia(@PathVariable String teachplanId) {
        //将课程计划id放在数组中，为调用service作准备
        String[] teachplanIds = new String[]{teachplanId};
        //通过service查询ES获取课程媒资信息
        QueryResponseResult queryResponseResult = esCourseService.getmedia(teachplanIds);
        QueryResult queryResult = queryResponseResult.getQueryResult();
        if (queryResult != null && queryResult.getList() != null && queryResult.getList().size() > 0) {
            //返回课程计划对应课程媒资
            return (TeachplanMediaPub) queryResult.getList().get(0);
        }
        return new TeachplanMediaPub();
    }
}
