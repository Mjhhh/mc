package com.edu.manage_cms.controller;

import com.edu.api.cms.CmsConfigControllerApi;
import com.edu.framework.domain.cms.CmsConfig;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseResult addCarousel(@RequestParam String url) {
        return cmsConfigService.addCarousel(url);
    }

    @Override
    @DeleteMapping("/carousel/del")
    public ResponseResult deleteCarousel(@RequestParam String url) {
        return cmsConfigService.deleteCarousel(url);
    }

}
