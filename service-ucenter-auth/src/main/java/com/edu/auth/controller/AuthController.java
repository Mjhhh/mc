package com.edu.auth.controller;

import com.edu.api.auth.AuthControllerApi;
import com.edu.auth.service.AuthService;
import com.edu.auth.util.ValidateUtil;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import com.edu.framework.domain.ucenter.request.LoginRequest;
import com.edu.framework.domain.ucenter.response.JwtResult;
import com.edu.framework.domain.ucenter.response.LoginResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.utils.CommonUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

@RestController
@RequestMapping("/")
public class AuthController implements AuthControllerApi {

    @Autowired
    AuthService authService;

    @Override
    @PostMapping("/userlogin")
    public LoginResult login(LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Override
    @PostMapping("/usermobilelogin")
    public LoginResult mobileLogin(LoginRequest loginRequest) {
        return authService.mobileLogin(loginRequest);
    }

    @Override
    @PostMapping("/registered")
    public LoginResult registered(McUserExt mcUserExt){
        return authService.registered(mcUserExt);
    }

    @Override
    @PostMapping("/userlogout")
    public ResponseResult logout() {
        return authService.logout();
    }

    @Override
    @GetMapping("/userjwt")
    public JwtResult userjwt() {
        return authService.userjwt();
    }

    @Override
    @GetMapping("/mobileValidateCode")
    public ResponseResult generateMobilevalidateCode(@RequestParam String phone) {
        return authService.generateMobilevalidateCode(phone);
    }

    @Override
    @GetMapping("/accountValidateCode")
    public CommonResponseResult accountValidateCode() throws Exception {
        return authService.accountValidateCode();
    }


}
