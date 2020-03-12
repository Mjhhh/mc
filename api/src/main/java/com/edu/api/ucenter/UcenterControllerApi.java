package com.edu.api.ucenter;

import com.edu.framework.domain.ucenter.ext.McUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户中心",tags = "用户中心管理")
public interface UcenterControllerApi {

    @ApiOperation("根据用户名查询账号信息")
    McUserExt getUserext(String username);

}
