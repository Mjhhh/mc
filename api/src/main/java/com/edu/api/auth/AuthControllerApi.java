package com.edu.api.auth;

import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.request.LoginRequest;
import com.edu.framework.domain.ucenter.response.JwtResult;
import com.edu.framework.domain.ucenter.response.LoginResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.text.ParseException;

/**
 * @author Admin
 */
@Api(value = "用户认证",tags = "用户认证接口")
public interface AuthControllerApi {

    @ApiOperation("登录")
    LoginResult login(LoginRequest loginRequest);

    @ApiOperation("注册")
    LoginResult registered(McUser mcuser);

    @ApiOperation("退出")
    ResponseResult logout();

    @ApiOperation("查询用户的jwt令牌")
    JwtResult userjwt();
}
