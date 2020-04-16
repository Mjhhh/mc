package com.edu.manage_cms.controller;


import com.edu.api.cms.CmsPageControllerApi;
import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.request.QueryPageRequest;
import com.edu.framework.domain.cms.response.CmsPageResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    CmsPageService cmsPageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    @PreAuthorize("hasAnyAuthority('cms_page_list')")
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        return cmsPageService.findList(page, size, queryPageRequest);
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return cmsPageService.add(cmsPage);
    }

    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return cmsPageService.getById(id);
    }


    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult edit(@PathVariable("id") String id,@RequestBody CmsPage cmsPage) {
        return cmsPageService.update(id, cmsPage);
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return cmsPageService.delete(id);
    }

    @Override
    @DeleteMapping("/delByCourseId/{courseId}")
    public ResponseResult deleteByCourseId(@PathVariable String courseId) {
        return cmsPageService.deleteByCourseId(courseId);
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    @PreAuthorize("hasAnyAuthority('cms_page_post')")
    public ResponseResult post(@PathVariable("pageId") String pageId) {
        return cmsPageService.postPage(pageId);
    }

    @Override
    @PostMapping("/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        return cmsPageService.save(cmsPage);
    }

    @Override
    @PostMapping("/postPageQuick")
    public CommonResponseResult postPageQuick(@RequestBody CmsPage cmsPage) {
        return cmsPageService.postPageQuick(cmsPage);
    }

    @Override
    @GetMapping("/findSiteList")
    @PreAuthorize("hasAnyAuthority('cms_page_sitelist')")
    public CommonResponseResult findSiteList() {
        return cmsPageService.findSiteList();
    }

    @Override
    @GetMapping("/findTemplate")
    @PreAuthorize("hasAnyAuthority('cms_page_templatelist')")
    public CommonResponseResult findTemplate() {
        return cmsPageService.findTemplate();
    }


}
