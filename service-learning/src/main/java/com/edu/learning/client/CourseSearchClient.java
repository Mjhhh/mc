package com.edu.learning.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.domain.course.response.TeachplanMediaPub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = ServiceList.SERVICE_SEARCH)
public interface CourseSearchClient {
    /**
     * 根据课程计划id查询课程媒资
     * @param teachplanId
     * @return
     */
    @GetMapping(value = "/search/course/getmedia/{teachplanId}")
    TeachplanMediaPub getmedia(@PathVariable String teachplanId);
}
