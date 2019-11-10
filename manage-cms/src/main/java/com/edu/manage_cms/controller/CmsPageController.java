package com.edu.manage_cms.controller;


import com.edu.api.cms.CmsPageControllerApi;
import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.request.QueryPageRequest;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.model.response.QueryResult;
import com.edu.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController //@Controller+@ResponseBody
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    PageService pageService;

    @Override
    @GetMapping("/list/{page}/{size}") //@RequestMapping并使用Get方法
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        return pageService.findList(page, size, queryPageRequest);
    }
}
