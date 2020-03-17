package com.edu.framework.domain.media.response;

import com.edu.framework.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;


/**
 * Created by Mjh on 2019-11-11
 */
@ToString
public enum MediaCode implements ResultCode {
    UPLOAD_FILE_REGISTER_FAIL(false,22001,"上传文件在系统注册失败，请刷新页面重试！"),
    UPLOAD_FILE_REGISTER_EXIST(false,22002,"上传文件在系统已存在！"),
    UPLOAD_FILE_REGISTER_CREATEFOLDER_FAIL(false,22003,"上传文件目录创建失败！"),
    UPLOAD_FILE_REGISTER_ISNULL(false,22003,"上传文件不存在！"),
    CHUNK_FILE_EXIST_CHECK(true,22004,"分块文件在系统已存在！"),
    CHUNK_FILE_NOT_EXIST_CHECK(true,22005,"分块文件在系统已存在！"),
    CHUNK_FILE_UPLOAD_FAIL(true,22006,"分块文件上传失败！"),
    MERGE_FILE_FAIL(false,22007,"合并文件失败，文件在系统已存在！"),
    MERGE_FILE_CREATEFAIL(false,22008,"创建合并文件失败！"),
    MERGE_FILE_CHECKFAIL(false,22009,"合并文件校验失败！");

    /**
     * 操作是否成功
     */
    boolean success;

    /**
     * 操作代码
     */
    int code;

    /**
     * 提示信息
     */
    String message;

    MediaCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
