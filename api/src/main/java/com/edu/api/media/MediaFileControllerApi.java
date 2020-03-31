package com.edu.api.media;

import com.edu.framework.domain.media.request.QueryMediaFileRequest;
import com.edu.framework.domain.media.response.CheckChunkResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "媒资文件管理", tags = "媒资文件管理接口")
public interface MediaFileControllerApi {

    @ApiOperation("查询文件列表")
    CommonResponseResult findList(int page, int size, QueryMediaFileRequest mediaFileRequest);

    @ApiOperation("删除文件")
    ResponseResult deleteVideo(String fileId);
}
