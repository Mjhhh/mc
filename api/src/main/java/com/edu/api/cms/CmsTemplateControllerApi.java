package com.edu.api.cms;

import com.edu.framework.domain.cms.CmsTemplate;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author mjh
 */
@Api(value = "cms模板管理接口", tags = "cms模板管理接口")
public interface CmsTemplateControllerApi {

    @ApiOperation("查找模板列表")
    CommonResponseResult findList(int page, int size, String templateName);

    @ApiOperation("添加模板页面")
    ResponseResult addTemplate(CmsTemplate cmsTemplate);

    @ApiOperation("编辑模板页面")
    ResponseResult editTemplate(CmsTemplate cmsTemplate);

    @ApiOperation("删除模板页面")
    ResponseResult delTemplate(String id);

    @ApiOperation("上传模板文件")
    ResponseResult uploadTemplateFile(MultipartFile multipartFile);


}
