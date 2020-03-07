package com.edu.framework.domain.media.request;

import com.edu.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryMediaFileRequest extends RequestData {

    @ApiModelProperty("文件原名")
    private String fileOriginalName;
    @ApiModelProperty("处理状态")
    private String processStatus;
    @ApiModelProperty("标签")
    private String tag;
}
