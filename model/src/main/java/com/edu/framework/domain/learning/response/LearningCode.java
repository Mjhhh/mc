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
    LEARNING_URL_ERROR(false, 23001, "获取视频播放地址失败");

    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;

    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code;

    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;
    LearningCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, LearningCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, LearningCode> builder = ImmutableMap.builder();
        for (LearningCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
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
