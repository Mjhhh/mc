package com.edu.framework.domain.ucenter.response;

import com.edu.framework.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;
import lombok.ToString;


/**
 * Created by Mjh on 2019-11-11
 * @author Admin
 */

@ToString
public enum AuthCode implements ResultCode {

    AUTH_USERNAME_NONE(false,23001,"请输入账号！"),
    AUTH_PASSWORD_NONE(false,23002,"请输入密码！"),
    AUTH_VERIFYCODE_NONE(false,23003,"请输入验证码！"),
    AUTH_ACCOUNT_NOTEXISTS(false,23004,"账号不存在！"),
    AUTH_CREDENTIAL_ERROR(false,23005,"账号或密码错误！"),
    AUTH_LOGIN_ERROR(false,23006,"登陆过程出现异常请尝试重新操作！"),
    AUTH_LOGIN_AUTHSERVER_NOTFOUND(false,23007,"未找到授权登录服务器！"),
    AUTH_LOGIN_APPLYTOKEN_FAIL(false,23007,"验证登录申请失败！"),
    AUTH_LOGIN_TOKEN_SAVEFAIL(false,23008,"授权登录令牌保存失败！"),
    AUTH_USER_IS_NULL(false,23009,"账号不能为空！"),
    AUTH_REGISTERED_USER_FAILURE(false,23010,"注册失败！");

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

    AuthCode(boolean success, int code, String message){
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
