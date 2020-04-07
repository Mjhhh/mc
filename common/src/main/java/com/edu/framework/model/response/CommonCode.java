package com.edu.framework.model.response;

import lombok.ToString;

/**
 * 数据返回的枚举类
 * @author Admin
 */

@ToString
public enum CommonCode implements ResultCode {

    SUCCESS(true, 200, "操作成功！"),
    SERVER_ERROR(false, 9999, "抱歉，系统繁忙，请稍后重试！"),
    FAIL(false, 10000, "操作失败！"),
    UNAUTHENTICATED(false, 10001, "此操作需要登陆系统！"),
    UNAUTHORISE(false, 10002, "权限不足，无权操作！"),
    AUTHENTICATION_DENIED(false, 10003, "认证被拒绝，请重新登录重试！"),
    INVALID_PARAM(false,10004,"非法参数！"),
    MISS_PARAM(false,10005,"参数缺失"),
    MISS_COMPANY_ID(false,10006,"请先加入组织"),
    OBJECT_IS_NOT_EXISTS(false,10007,"对象不存在");

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
