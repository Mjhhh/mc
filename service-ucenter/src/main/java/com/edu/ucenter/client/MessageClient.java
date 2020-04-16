package com.edu.ucenter.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.message.McMessage;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.response.McCompanyResult;
import com.edu.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = ServiceList.SERVICE_MESSAGE)
public interface MessageClient {

    @PostMapping("/msg/addmsg")
    ResponseResult addMsg(@RequestBody McMessage mcMessage);
}
