package com.edu.auth.controller;

import com.edu.api.auth.AuthControllerApi;
import com.edu.auth.service.AuthService;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.request.LoginRequest;
import com.edu.framework.domain.ucenter.response.JwtResult;
import com.edu.framework.domain.ucenter.response.LoginResult;
import com.edu.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/registered")
    public LoginResult registered(McUser mcuser){
        return authService.registered(mcuser);
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
}
