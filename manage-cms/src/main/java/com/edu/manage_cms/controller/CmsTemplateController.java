package com.edu.manage_cms.controller;

import com.edu.api.cms.CmsTemplateControllerApi;
import com.edu.framework.domain.cms.CmsTemplate;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {

    @Autowired
    CmsTemplateService cmsTemplateService;

    @Override
    @GetMapping("/list/{page}/{size}")
    @PreAuthorize("hasAnyAuthority('cms_template_list')")
    public CommonResponseResult findList(@PathVariable int page,@PathVariable int size,@RequestParam(required = false) String templateName) {
        return cmsTemplateService.findList(page, size, templateName);
    }

    @Override
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('cms_template_add')")
    public ResponseResult addTemplate(@RequestBody CmsTemplate cmsTemplate) {
        return cmsTemplateService.addTemplate(cmsTemplate);
    }

    @Override
    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('cms_template_edit')")
    public ResponseResult editTemplate(@RequestBody CmsTemplate cmsTemplate) {
        return cmsTemplateService.editTemplate(cmsTemplate);
    }

    @Override
    @DeleteMapping("/del/{id}")
    @PreAuthorize("hasAnyAuthority('cms_template_del')")
    public ResponseResult delTemplate(@PathVariable String id) {
        return cmsTemplateService.delTemplate(id);
    }

    @Override
    @PostMapping("/upload")
    public ResponseResult uploadTemplateFile(@RequestParam("file") MultipartFile multipartFile) {
        return cmsTemplateService.uploadTemplateFile(multipartFile);
    }


}
