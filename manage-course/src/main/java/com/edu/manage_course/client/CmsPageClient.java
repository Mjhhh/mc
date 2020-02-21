package com.edu.manage_course.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.response.CmsPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = ServiceList.SERVER_MANAGE_CMS)
public interface CmsPageClient {

    @GetMapping("/cms/page/get/{id}")
    CmsPage findById(@PathVariable String id);

    @PostMapping("/cms/page/save")
    CmsPageResult save(@RequestBody CmsPage cmsPage);
}

