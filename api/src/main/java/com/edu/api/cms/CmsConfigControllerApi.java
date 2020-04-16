package com.edu.api.cms;

import com.edu.framework.domain.cms.CmsConfig;
import com.edu.framework.domain.course.Category;
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

    @ApiOperation("根据id查询页面配置")
    CmsConfig getModel(String id);

    @ApiOperation("查询轮播图配置信息")
    CommonResponseResult getCarousel();

    @ApiOperation("查询所有cms配置信息")
    List<CmsConfig> findList();

    @ApiOperation("添加轮播图片")
    ResponseResult addCarousel(String url);

    @ApiOperation("删除轮播图片")
    ResponseResult deleteCarousel(String url);

    @ApiOperation("获取课程分类列表")
    CommonResponseResult findCategoryList();

    @ApiOperation("添加cms分类配置信息")
    ResponseResult addCmsConfigByCategory(List<String> ids);

    @ApiOperation("获取cms分类配置信息")
    CommonResponseResult findCategoryCmsConfig();

    @ApiOperation("添加cms课程推荐配置信息")
    ResponseResult addCmsCourseRecomment(List<String> ids);

    @ApiOperation("添加cms新上好课配置信息")
    ResponseResult addCmsNewCourse(List<String> ids);

    @ApiOperation("添加课程分类信息")
    ResponseResult addCourseCategory(Category category);

    @ApiOperation("删除课程分类")
    ResponseResult delCourseCategory(String id);

    @ApiOperation("课程信息列表")
    CommonResponseResult findCourseList(int page, int size);
}
