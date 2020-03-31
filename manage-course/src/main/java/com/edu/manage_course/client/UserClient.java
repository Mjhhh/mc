package com.edu.manage_course.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.response.McCompanyResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = ServiceList.SERVICE_UCENTER)
public interface UserClient {

    @GetMapping("/ucenter/company/get")
    McCompanyResult getCompany(@RequestParam String companyId);

    @GetMapping("/ucenter/user/listbyids")
    List<McUser> findUserListByIds(@RequestParam List<String> ids);
}
