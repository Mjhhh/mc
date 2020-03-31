package com.edu.manage_course.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.response.CmsPageResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = ServiceList.SERVER_MANAGE_CMS)
public interface CmsPageClient {

    /**
     * 通过ID查询页面
     */
    @GetMapping("/cms/page/get/{id}")
    CmsPage findById(@PathVariable String id);

    /**
     * 保存页面
     */
    @PostMapping("/cms/page/save")
    CmsPageResult save(@RequestBody CmsPage cmsPage);

    /**
     * 一键发布页面
     */
    @PostMapping("/cms/page/postPageQuick")
    CommonResponseResult postPageQuick(@RequestBody CmsPage cmsPage);

    /**
     * 根据课程id删除页面
     * @param courseId
     * @return
     */
    @DeleteMapping("/cms/page/delByCourseId/{courseId}")
    ResponseResult deleteByCourseId(@PathVariable String courseId);
}

