package com.edu.framework.domain.learning.response;

import com.edu.framework.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;


/**
 * Created by Mjh on 2019-11-11
 */
@ToString
public enum LearningCode implements ResultCode {
    LEARNING_URL_ERROR(false, 23001, "获取视频播放地址失败"),
    LEARNING_GET_MEDIA_ERROR(false, 23002, "获取媒资失败"),
    CHOOSECOURSE_USERISNULl(false, 23003, "用户id为空"),
    CHOOSECOURSE_TASKISNULL(false, 23004, "任务记录为空");

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

    LearningCode(boolean success, int code, String message){
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
