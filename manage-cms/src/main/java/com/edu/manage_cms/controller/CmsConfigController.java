package com.edu.manage_cms.controller;

import com.edu.api.cms.CmsConfigControllerApi;
import com.edu.framework.domain.cms.CmsConfig;
import com.edu.framework.domain.course.Category;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mjh
 * cms配置信息接口
 */
@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {

    @Autowired
    private CmsConfigService cmsConfigService;

    @Override
    @GetMapping("/getModel/{id}")
    public CmsConfig getModel(@PathVariable("id") String id) {
        return cmsConfigService.getConfigById(id);
    }

    @Override
    @GetMapping("/getCarousel")
    @PreAuthorize("hasAnyAuthority('cms_config_carousel')")
    public CommonResponseResult getCarousel() {
        return cmsConfigService.getCarousel();
    }

    @Override
    @GetMapping("/list")
    public List<CmsConfig> findList() {
        return cmsConfigService.findList();
    }

    @Override
    @PostMapping("/carousel/add")
    @PreAuthorize("hasAnyAuthority('cms_config_add_carousel')")
    public ResponseResult addCarousel(@RequestParam String url) {
        return cmsConfigService.addCarousel(url);
    }

    @Override
    @DeleteMapping("/carousel/del")
    @PreAuthorize("hasAnyAuthority('cms_config_del_carousel')")
    public ResponseResult deleteCarousel(@RequestParam String url) {
        return cmsConfigService.deleteCarousel(url);
    }

    @Override
    @GetMapping("/category/list")
    public CommonResponseResult findCategoryList() {
        return cmsConfigService.findCategoryList();
    }

    @Override
    @PostMapping("/category/addCategoryCmsConfig")
    @PreAuthorize("hasAnyAuthority('cms_config_add_category')")
    public ResponseResult addCmsConfigByCategory(@RequestBody List<String> ids) {
        return cmsConfigService.addCmsConfigByCategory(ids);
    }

    @Override
    @GetMapping("/category/findCmsConfig")
    public CommonResponseResult findCategoryCmsConfig() {
        return cmsConfigService.findCategoryCmsConfig();
    }

    @Override
    @PostMapping("/recomment/add")
    @PreAuthorize("hasAnyAuthority('cms_config_add_recomment')")
    public ResponseResult addCmsCourseRecomment(@RequestBody List<String> ids) {
        return cmsConfigService.addCmsCourseRecomment(ids);
    }

    @Override
    @PostMapping("/newcourse/add")
    @PreAuthorize("hasAnyAuthority('cms_config_add_newcourse')")
    public ResponseResult addCmsNewCourse(@RequestBody List<String> ids) {
        return cmsConfigService.addCmsNewCourse(ids);
    }

    @Override
    @PostMapping("/category/addCourseCategory")
    @PreAuthorize("hasAnyAuthority('cms_config_add_course_category')")
    public ResponseResult addCourseCategory(@RequestBody Category category) {
        return cmsConfigService.addCourseCategory(category);
    }

    @Override
    @DeleteMapping("/category/delCourseCategory/{id}")
    @PreAuthorize("hasAnyAuthority('cms_config_del_course_category')")
    public ResponseResult delCourseCategory(@PathVariable String id) {
        return cmsConfigService.delCourseCategory(id);
    }

    @Override
    @GetMapping("/course/list/{page}/{size}")
    public CommonResponseResult findCourseList(@PathVariable int page,@PathVariable int size) {
        return cmsConfigService.findCourseList(page, size);
    }
}
