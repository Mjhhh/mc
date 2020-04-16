package com.edu.api.ucenter;

import com.edu.framework.domain.ucenter.*;
import com.edu.framework.domain.ucenter.ext.McRoleExt;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import com.edu.framework.domain.ucenter.ext.McUserExtRole;
import com.edu.framework.domain.ucenter.request.QueryMcUserRequest;
import com.edu.framework.domain.ucenter.response.McCompanyResult;
import com.edu.framework.domain.ucenter.response.McUserResult;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.Response;
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
    ResponseResult editUser(McUserExtRole mcUserExtRole);

    @ApiOperation("用户绑定手机号码")
    ResponseResult userBindPhone(String phone, String code);

    @ApiOperation("用户修改密码")
    ResponseResult changePassword(String oldPassword, String newPassword);

    @ApiOperation("删除用户信息")
    ResponseResult deltUser(String username);

    @ApiOperation("查询用户列表")
    CommonResponseResult findUserList(int page, int size, QueryMcUserRequest request);

    @ApiOperation("查询用户名和用户主键列表")
    CommonResponseResult findUserIdAndUserNameList();

    @ApiOperation("查询用户列表")
    List<McUser> findUserListByIds(List<String> ids);

    @ApiOperation("根据用户名查询用户")
    McUser get(String username);

    @ApiOperation("根据昵称查询用户")
    McUser getByname(String nickname);

    @ApiOperation("根据用户名id查询用户")
    McUser getById(String username);

    @ApiOperation("根据手机号码查询用户")
    McUser getByPhone(String phone);

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

    @ApiOperation("编辑权限")
    ResponseResult editPermission(McMenu mcMenu);

    @ApiOperation("删除权限")
    ResponseResult delPermission(String id);

    @ApiOperation("组织列表")
    CommonResponseResult findCompanyList(int page, int size);

    @ApiOperation("添加组织")
    ResponseResult addCompany(McCompany mcCompany);

    @ApiOperation("修改组织")
    ResponseResult editCompany(McCompany mcCompany);

    @ApiOperation("查找组织信息")
    McCompanyResult getCompany(String companyId);

    @ApiOperation("查找组织信息")
    McCompanyResult getCompanyByUser(String userId);

    @ApiOperation("获取组织人员")
    CommonResponseResult findCompanyUserList(int page, int size);

    @ApiOperation("获取组织人员")
    CommonResponseResult findCompanyUser();

    @ApiOperation("邀请用户加入组织")
    ResponseResult inviteUser(String username,String name);

    @ApiOperation("从组织中移除用户")
    ResponseResult delCompanyUser(String username);

    @ApiOperation("根据用户id从组织中移除用户")
    ResponseResult delCompanyUserByUserId(String userId);

    @ApiOperation("获取邀请记录")
    McCompanyUser getCompanyUser(String userId);

    @ApiOperation("用户接受邀请")
    ResponseResult accpetInvite(String userId);
}
