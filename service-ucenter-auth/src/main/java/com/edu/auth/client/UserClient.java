package com.edu.auth.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import com.edu.framework.domain.ucenter.response.McUserResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = ServiceList.SERVICE_UCENTER)
public interface UserClient {

    @GetMapping("/ucenter/user/getuserext")
    McUserExt getUserext(@RequestParam String username);

    @PostMapping("/ucenter/user/registered")
    McUserResult registered(@RequestBody McUser mcUser);

    @GetMapping("/ucenter/user/get/{username}")
    McUser get(@PathVariable String username);

}
