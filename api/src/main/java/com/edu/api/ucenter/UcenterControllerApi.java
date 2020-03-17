package com.edu.api.ucenter;

import com.edu.framework.domain.ucenter.McMenu;
import com.edu.framework.domain.ucenter.McRole;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.ext.McRoleExt;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import com.edu.framework.domain.ucenter.request.QueryMcUserRequest;
import com.edu.framework.domain.ucenter.response.McUserResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value = "用户中心",tags = "用户中心管理")
public interface UcenterControllerApi {

    @ApiOperation("根据用户名查询账号信息")
    McUserExt getUserext(String username);

    @ApiOperation("用户注册")
    McUserResult registered(McUser mcUser);

    @ApiOperation("修改用户信息")
    ResponseResult editUser(McUser mcUser);

    @ApiOperation("查询用户列表")
    CommonResponseResult findUserList(int page, int size, QueryMcUserRequest request);

    @ApiOperation("查询用户")
    McUser get(String username);

    @ApiOperation("重置密码")
    ResponseResult resetPassword(String username);

    @ApiOperation("添加角色")
    ResponseResult addRole(McRoleExt mcRoleExt);

    @ApiOperation("编辑角色")
    ResponseResult editRole(McRoleExt mcRoleExt);

    @ApiOperation("删除角色")
    ResponseResult delRole(String id);

    @ApiOperation("批量删除角色")
    ResponseResult delRoleList(List<String> ids);

    @ApiOperation("角色列表")
    CommonResponseResult findRoleList(int page, int size);

    @ApiOperation("权限精简列表")
    CommonResponseResult findPermissionSimpleList();

    @ApiOperation("角色权限精简列表")
    CommonResponseResult findPermissionSimpleListByRole(String roleId);

    @ApiOperation("查询权限")
    CommonResponseResult findPermission();

    @ApiOperation("添加权限")
    ResponseResult addPerimission(McMenu mcMenu);
}
