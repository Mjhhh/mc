package com.edu.api.filesystem;

import com.edu.framework.domain.filesystem.response.UploadFileResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Admin
 */
@Api(tags = "文件管理系统", description = "负责文件的上传，下载")
public interface FileSystemControllerApi {

    @ApiOperation(value = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "文件标识", name = "filetag", paramType = "query", dataType = "String"),
            @ApiImplicitParam(value = "业务标识", name = "businesskey", paramType = "query", dataType = "String"),
            @ApiImplicitParam(value = "文件元信息", name = "metadata", paramType = "query", dataType = "String"),
    })
    UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata);

    @ApiOperation(value = "删除文件")
    ResponseResult delete(String fileId);
}
