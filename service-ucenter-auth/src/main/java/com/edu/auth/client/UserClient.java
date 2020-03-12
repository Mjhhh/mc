package com.edu.auth.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = ServiceList.SERVICE_UCENTER)
public interface UserClient {

    @GetMapping("/ucenter/getuserext")
    McUserExt getUserext(@RequestParam String username);
}
