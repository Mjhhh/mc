package com.edu.ucenter.controller;

import com.edu.api.ucenter.UcenterControllerApi;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import com.edu.ucenter.service.UcenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi {

    @Autowired
    UcenterService ucenterService;

    @Override
    @GetMapping("/getuserext")
    public McUserExt getUserext(@RequestParam String username) {
        return ucenterService.getUserext(username);
    }
}
