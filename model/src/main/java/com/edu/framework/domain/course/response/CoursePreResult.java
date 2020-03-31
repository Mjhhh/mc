package com.edu.framework.domain.course.response;

import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 课程发布请求响应类
 */
@Data
@NoArgsConstructor
public class CoursePreResult extends ResponseResult {
    List<CourseView> courseViewList;
    Long total;
    public CoursePreResult(ResultCode resultCode, List<CourseView> courseViewList, Long total) {
        super(resultCode);
        this.courseViewList = courseViewList;
        this.total = total;
    }
}
