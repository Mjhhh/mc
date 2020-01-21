package com.edu.framework.domain.course.response;

import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.model.response.ResultCode;
import lombok.Data;

/**
 * course通用数据返回类
 */
@Data
public class CourseCommonResult extends ResponseResult {
    Object data;
    public CourseCommonResult(ResultCode resultCode, Object data) {
        super(resultCode);
        this.data = data;
    }

}
