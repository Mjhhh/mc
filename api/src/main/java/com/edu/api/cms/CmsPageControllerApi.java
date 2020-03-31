package com.edu.api.cms;

import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.response.CmsPageResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.domain.cms.request.QueryPageRequest;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.*;

@Api(value = "cms页面管理接口", tags = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    @ApiOperation("分页查询页面列表")
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation("添加页面")
    CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("通过ID查询页面")
    CmsPage findById(String id);

    @ApiOperation("修改页面")
    CmsPageResult edit(String id, CmsPage cmsPage);

    @ApiOperation("刪除页面")
    ResponseResult delete(String id);

    @ApiOperation("根据课程id删除页面")
    ResponseResult deleteByCourseId(String id);

    @ApiOperation("发布页面")
    ResponseResult post(String pageId);

    @ApiOperation("保存页面")
    CmsPageResult save(CmsPage cmsPage);

    @ApiOperation("一键发布页面")
    CommonResponseResult postPageQuick(CmsPage cmsPage);

    @ApiOperation("获取站点列表")
    CommonResponseResult findSiteList();

    @ApiOperation("获取模板列表")
    CommonResponseResult findTemplate();
}
