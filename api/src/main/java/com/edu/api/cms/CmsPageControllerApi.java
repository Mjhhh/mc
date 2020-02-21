package com.edu.api.cms;

import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.response.CmsPageResult;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.domain.cms.request.QueryPageRequest;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.*;

@Api(value = "cms页面管理接口", tags = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation("添加页面")
    CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("通过ID查询页面")
    @ApiImplicitParam(name = "id", value = "页面id", required = true, paramType = "path", dataType = "String")
    CmsPage findById(String id);

    @ApiOperation("修改页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "页面id", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "cmsPage", value = "页面信息", required = true, paramType = "body", dataType = "CmsPage")
    })
    CmsPageResult edit(String id, CmsPage cmsPage);

    @ApiOperation("刪除页面")
    @ApiImplicitParam(name = "id", value = "页面id", required = true, paramType = "path", dataType = "String")
    ResponseResult delete(String id);

    @ApiOperation("发布页面")
    @ApiImplicitParam(name = "pageId", value = "页面id", required = true, paramType = "path", dataType = "String")
    ResponseResult post(String pageId);

    @ApiOperation("保存页面")
    CmsPageResult save(CmsPage cmsPage);

}
