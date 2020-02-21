package com.edu.manage_cms.controller;

import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.web.BaseController;
import com.edu.manage_cms.client.CourseClient;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
@Api(value = "freemarker模板页面测试", tags = "freemarker模板页面测试控制器")
public class FreeMarkerController extends BaseController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CourseClient courseClient;

    @RequestMapping("/freemarker/testtemplate")
    public String testTemplate(Map<String, Object> map){
        //设置模型数据
        CourseView courseview = courseClient.courseview("4028e581617f945f01617f9dabc40000");
        map.put("courseBase", courseview.getCourseBase());
        map.put("courseMarket", courseview.getCourseMarket());
        map.put("coursePic", courseview.getCoursePic());
        map.put("teachplanNode", courseview.getTeachplanNode());
        return "course";
    }
}
