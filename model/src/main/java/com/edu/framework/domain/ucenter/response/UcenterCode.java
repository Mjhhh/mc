package com.edu.framework.domain.ucenter.response;

import com.edu.framework.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;


/**
 * Created by Mjh on 2019-11-11
 */
@ToString
public enum UcenterCode implements ResultCode {
    UCENTER_USERNAME_NONE(false,23001,"请输入账号！"),
    UCENTER_PASSWORD_NONE(false,23002,"请输入密码！"),
    UCENTER_VERIFYCODE_NONE(false,23003,"请输入验证码！"),
    UCENTER_ACCOUNT_NOTEXISTS(false,23004,"账号不存在！"),
    UCENTER_CREDENTIAL_ERROR(false,23005,"账号或密码错误！"),
    UCENTER_LOGIN_ERROR(false,23006,"登陆过程出现异常请尝试重新操作！"),
    UCENTER_USERNAME_OR_PASSWORD_NONE(false,23007,"账号或密码为空！"),
    UCENTER_ACCOUNT_ALREADY_EXISTS(false,23008,"账号已经存在！"),
    UCENTER_USERNAME_IS_NULL(false,23009,"用户名不能为空！"),
    UCENTER_PARAMS_IS_NULL(false,23010,"重要参数不能为空！"),
    UCENTER_ROLE_ALREADY_EXISTS(false,230011,"角色已经存在！"),
    UCENTER_ROLE_NOT_EXISTS(false,230012,"角色不存在！"),
    UCENTER_PERMISSION_NOT_EXISTS(false,230013,"权限不存在！"),
    UCENTER_COMPANY_NOT_EXISTS(false,230014,"公司不存在！"),
    UCENTER_ALREADY_HAVE_COMPANY(false,230015,"用户已经拥有组织！"),
    PHONE_IS_ALREADY_EXISTS(false,230016,"手机号码已经存在！"),
    SEND_VERIFICATION_ERROR(false,230018,"发送验证码失败！"),
    VERIFICATION_CODE_ERROR(false,230017,"验证码错误！");

    boolean success;

    int code;

    String message;

    UcenterCode(boolean success, int code, String message){
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
