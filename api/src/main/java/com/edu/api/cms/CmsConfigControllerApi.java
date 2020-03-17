package com.edu.api.cms;

import com.edu.framework.domain.cms.CmsConfig;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author mjh
 */
@Api(value = "cms配置管理接口", tags = "cms配置模型管理接口")
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询cms配置信息")
    CmsConfig getModel(String id);

    @ApiOperation("获取轮播图配置信息")
    CommonResponseResult getCarousel();

    @ApiOperation("查询所有cms配置信息")
    List<CmsConfig> findList();

    @ApiOperation("添加轮播图片")
    ResponseResult addCarousel(String url);

    @ApiOperation("删除轮播图片")
    ResponseResult deleteCarousel(String url);

}
