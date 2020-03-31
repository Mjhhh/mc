package com.edu.api.auth;

import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import com.edu.framework.domain.ucenter.request.LoginRequest;
import com.edu.framework.domain.ucenter.response.JwtResult;
import com.edu.framework.domain.ucenter.response.LoginResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * @author Admin
 */
@Api(value = "用户认证",tags = "用户认证接口")
public interface AuthControllerApi {

    @ApiOperation("登录")
    LoginResult login(LoginRequest loginRequest);

    @ApiOperation("手机登录")
    LoginResult mobileLogin(LoginRequest loginRequest);

    @ApiOperation("注册")
    LoginResult registered(McUserExt mcUserExt);

    @ApiOperation("退出")
    ResponseResult logout();

    @ApiOperation("查询用户的jwt令牌")
    JwtResult userjwt();

    @ApiOperation("生成手机验证码")
    ResponseResult generateMobilevalidateCode(String phone);

    @ApiOperation("生成图片验证码")
    CommonResponseResult accountValidateCode() throws Exception;
}
