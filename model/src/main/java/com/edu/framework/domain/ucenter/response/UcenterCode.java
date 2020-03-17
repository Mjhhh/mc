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
    UCENTER_ROLE_NOT_EXISTS(false,230012,"角色不存在！");

    //操作代码
    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;

    //操作代码
    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code;
    //提示信息
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;
    private UcenterCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, UcenterCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, UcenterCode> builder = ImmutableMap.builder();
        for (UcenterCode commonCode : values()) {
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
