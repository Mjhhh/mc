package com.edu.api.cms;

import com.edu.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "数据字典接口",tags = "提供数据字典接口的管理、查询功能")
public interface SysDicthinaryControllerApi {

    @ApiOperation(value = "数据字典查询接口")
    SysDictionary getByType(String type);
}
