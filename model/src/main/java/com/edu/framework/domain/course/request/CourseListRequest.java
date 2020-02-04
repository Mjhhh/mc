package com.edu.framework.domain.course.request;

import com.edu.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseListRequest extends RequestData {

    @ApiModelProperty("公司ID")
    private String companyId;
}
