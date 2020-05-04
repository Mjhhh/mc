package com.edu.ucenter.controller;

import com.edu.api.ucenter.UcenterControllerApi;
import com.edu.framework.domain.ucenter.*;
import com.edu.framework.domain.ucenter.ext.McRoleExt;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import com.edu.framework.domain.ucenter.ext.McUserExtRole;
import com.edu.framework.domain.ucenter.request.QueryMcUserRequest;
import com.edu.framework.domain.ucenter.response.McCompanyResult;
import com.edu.framework.domain.ucenter.response.McUserResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.ucenter.service.UcenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi {

    @Autowired
    UcenterService ucenterService;

    @Override
    @GetMapping("/user/getuserext")
    public McUserExt getUserext(@RequestParam String username) {
        return ucenterService.getUserext(username);
    }

    @Override
    @PostMapping("/user/registered")
    public McUserResult registered(@RequestBody McUser mcUser) {
        return ucenterService.registered(mcUser);
    }

    @Override
    @PutMapping("/user/edit")
    public ResponseResult editUser(@RequestBody McUserExtRole mcUserExtRole) {
        return ucenterService.editUser(mcUserExtRole);
    }

    @Override
    @PutMapping("/user/editphone")
    public ResponseResult userBindPhone(@RequestParam String phone, @RequestParam String code) {
        return ucenterService.userBindPhone(phone, code);
    }

    @Override
    @PutMapping("/user/changepassword")
    public ResponseResult changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        return ucenterService.changePassword(oldPassword, newPassword);
    }

    @Override
    @DeleteMapping("/user/del/{username}")
    @PreAuthorize("hasAnyAuthority('ucenter_user_del')")
    public ResponseResult deltUser(@PathVariable String username) {
        return ucenterService.deltUser(username);
    }

    @Override
    @GetMapping("/user/list/{page}/{size}")
    @PreAuthorize("hasAnyAuthority('ucenter_user_list')")
    public CommonResponseResult findUserList(@PathVariable int page, @PathVariable int size, QueryMcUserRequest request) {
        return ucenterService.findList(page, size, request);
    }

    @Override
    @GetMapping("/user/namelist")
    public CommonResponseResult findUserIdAndUserNameList() {
        return ucenterService.findUserIdAndUserNameList();
    }

    @Override
    @GetMapping("/user/listbyids")
    public List<McUser> findUserListByIds(@RequestParam List<String> ids) {
        return ucenterService.findUserListByIds(ids);
    }

    @Override
    @GetMapping("/user/get/{username}")
    public McUser get(@PathVariable String username) {
        return ucenterService.get(username);
    }

    @Override
    @GetMapping("/user/getbyname/{nickname}")
    public McUser getByname(@PathVariable String nickname) {
        return ucenterService.getByName(nickname);
    }

    @Override
    @GetMapping("/user/getuser/{userid}")
    public McUser getById(@PathVariable String userid) {
        return ucenterService.getById(userid);
    }

    @Override
    @GetMapping("/user/getbyphone/{phone}")
    public McUser getByPhone(@PathVariable String phone) {
        return ucenterService.getByPhone(phone);
    }

    @Override
    @PutMapping("/user/reset/{username}")
    public ResponseResult resetPassword(@PathVariable String username) {
        return ucenterService.resetPassword(username);
    }

    @Override
    @PostMapping("/role/add")
    @PreAuthorize("hasAnyAuthority('ucenter_role_add')")
    public ResponseResult addRole(@RequestBody McRoleExt mcRoleExt) {
        return ucenterService.addRole(mcRoleExt);
    }

    @Override
    @PutMapping("/role/edit")
    @PreAuthorize("hasAnyAuthority('ucenter_role_edit')")
    public ResponseResult editRole(@RequestBody McRoleExt mcRoleExt) {
        return ucenterService.editRole(mcRoleExt);
    }

    @Override
    @DeleteMapping("/role/del/{id}")
    @PreAuthorize("hasAnyAuthority('ucenter_role_del')")
    public ResponseResult delRole(@PathVariable String id) {
        return ucenterService.delRole(id);
    }

    @Override
    @DeleteMapping("/role/delList")
    public ResponseResult delRoleList(@RequestBody List<String> ids) {
        return ucenterService.delRoleList(ids);
    }

    @Override
    @GetMapping("/role/list/{page}/{size}")
    @PreAuthorize("hasAnyAuthority('ucenter_role_list')")
    public CommonResponseResult findRoleList(@PathVariable int page, @PathVariable int size) {
        return ucenterService.findRoleList(page, size);
    }

    @Override
    @GetMapping("/permission/simplelist")
    public CommonResponseResult findPermissionSimpleList() {
        return ucenterService.findPermissionSimpleList();
    }

    @Override
    @GetMapping("/permission/simplelist/role/{roleId}")
    public CommonResponseResult findPermissionSimpleListByRole(@PathVariable String roleId) {
        return ucenterService.findPermissionSimpleListByRole(roleId);
    }

    @Override
    @GetMapping("/permission/findPermission")
    @PreAuthorize("hasAnyAuthority('ucenter_permission_list')")
    public CommonResponseResult findPermission() {
        return ucenterService.findPermission();
    }

    @Override
    @PostMapping("/permission/add")
    @PreAuthorize("hasAnyAuthority('ucenter_permission_add')")
    public ResponseResult addPerimission(@RequestBody McMenu mcMenu) {
        return ucenterService.addPerimission(mcMenu);
    }

    @Override
    @PutMapping("/permission/edit")
    @PreAuthorize("hasAnyAuthority('ucenter_permission_edit')")
    public ResponseResult editPermission(@RequestBody McMenu mcMenu) {
        return ucenterService.editPermission(mcMenu);
    }

    @Override
    @DeleteMapping("/permission/del/{id}")
    @PreAuthorize("hasAnyAuthority('ucenter_permission_del')")
    public ResponseResult delPermission(@PathVariable String id) {
        return ucenterService.delPermission(id);
    }

    @Override
    @GetMapping("/company/list/{page}/{size}")
    @PreAuthorize("hasAnyAuthority('ucenter_company_list')")
    public CommonResponseResult findCompanyList(@PathVariable int page, @PathVariable int size) {
        return ucenterService.findCompanyList(page, size);
    }

    @Override
    @PostMapping("/company/add")
    public ResponseResult addCompany(@RequestBody McCompany mcCompany) {
        return ucenterService.addCompany(mcCompany);
    }

    @Override
    @PutMapping("/company/edit")
    public ResponseResult editCompany(@RequestBody McCompany mcCompany) {
        return ucenterService.editCompany(mcCompany);
    }

    @Override
    @GetMapping("/company/get")
    public McCompanyResult getCompany(@RequestParam String companyId) {
        return ucenterService.getCompany(companyId);
    }

    @Override
    @GetMapping("/company/getbyuser")
    public McCompanyResult getCompanyByUser(@RequestParam String userId) {
        return ucenterService.getCompanyByUser(userId);
    }

    @Override
    @GetMapping("/company/user/list/{page}/{size}")
    @PreAuthorize("hasAnyAuthority('ucenter_company_user_list')")
    public CommonResponseResult findCompanyUserList(@PathVariable int page, @PathVariable int size) {
        return ucenterService.findCompanyUserList(page, size);
    }

    @Override
    @GetMapping("/company/user/getlist")
    public CommonResponseResult findCompanyUser() {
        return ucenterService.findCompanyUser();
    }

    @Override
    @PostMapping("/company/user/invite")
    public ResponseResult inviteUser(@RequestParam String username, @RequestParam String name) {
        return ucenterService.inviteUser(username, name);
    }

    @Override
    @DeleteMapping("/company/user/delete")
    public ResponseResult delCompanyUser(String username) {
        return ucenterService.delCompanyUser(username);
    }

    @Override
    @DeleteMapping("/company/user/deleteByUserId")
    public ResponseResult delCompanyUserByUserId(@RequestParam String userId) {
        return ucenterService.delCompanyUserByUserId(userId);
    }

    @Override
    @GetMapping("/company/user/get")
    public McCompanyUser getCompanyUser(@RequestParam String userId) {
        return ucenterService.getCompanyUser(userId);
    }

    @Override
    @PutMapping("/company/user/accpetinvite")
    public ResponseResult accpetInvite(@RequestParam String userId) {
        return ucenterService.accpetInvite(userId);
    }


}
