package com.edu.manage_cms.controller;

import com.edu.framework.web.BaseController;
import com.edu.manage_cms.service.CmsPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author mjh
 */
@Controller
@Api(value = "cms页面预览接口", tags = "cms页面预览接口，提供页面预览接口")
public class CmsPagePreviewController extends BaseController {

    @Autowired
    CmsPageService cmsPageService;

    @ApiOperation("页面预览")
    @GetMapping("/cms/preview/{pageId}")
    public void preview(@PathVariable String pageId) throws IOException {
        String pageHtml = cmsPageService.getPageHtml(pageId);
        if (StringUtils.isNotEmpty(pageHtml)) {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-type", "text/html;charset=utf-8");
            outputStream.write(pageHtml.getBytes(StandardCharsets.UTF_8));
        }
    }

}
