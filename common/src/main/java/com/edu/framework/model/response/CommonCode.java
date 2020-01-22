package com.edu.framework.model.response;

import lombok.ToString;

/**
 * 数据返回的枚举类
 */

@ToString
public enum CommonCode implements ResultCode {

    SUCCESS(true, 200, "操作成功！"),
    FAIL(false, 1100, "操作失败！"),
    UNAUTHENTICATED(false, 1001, "此操作需要登陆系统！"),
    UNAUTHORISE(false, 1002, "权限不足，无权操作！"),
    SERVER_ERROR(false, 9999, "抱歉，系统繁忙，请稍后重试！"),
    INVALID_PARAM(false,10003,"非法参数！"),
    MISS_PARAM(false,10004,"参数缺失");

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    CommonCode(boolean success, int code, String message) {
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
