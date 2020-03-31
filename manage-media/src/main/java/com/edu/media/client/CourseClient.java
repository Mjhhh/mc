package com.edu.media.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.domain.course.response.CoursePreResult;
import com.edu.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = ServiceList.SERVER_MANAGE_COURSE)
public interface CourseClient {

    @DeleteMapping("/course/teachplan/media/del/{mediaId}")
    ResponseResult delTeachplanMedia(@PathVariable String mediaId);

}
