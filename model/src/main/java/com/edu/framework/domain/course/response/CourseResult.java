package com.edu.framework.domain.course.response;

import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程发布请求响应类
 */
@Data
@NoArgsConstructor
public class CourseResult extends ResponseResult {
    String pageUrl;
    public CourseResult(ResultCode resultCode, String pageUrl) {
        super(resultCode);
        this.pageUrl = pageUrl;
    }
}
