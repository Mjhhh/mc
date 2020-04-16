package com.edu.message.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.ucenter.McCompanyUser;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.response.McCompanyResult;
import com.edu.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = ServiceList.SERVICE_UCENTER)
public interface UserClient {

    @GetMapping("/ucenter/company/get")
    McCompanyResult getCompany(@RequestParam String companyId);

    @GetMapping("/ucenter/user/getuser/{userid}")
    McUser getById(@PathVariable String userid);

    @GetMapping("/ucenter/company/user/get")
    McCompanyUser getCompanyUser(@RequestParam String userId);

    @DeleteMapping("/ucenter/company/user/deleteByUserId")
    ResponseResult delCompanyUserByUserId(@RequestParam String userId);

    @PutMapping("/ucenter/company/user/accpetinvite")
    ResponseResult accpetInvite(@RequestParam String userId);
}
