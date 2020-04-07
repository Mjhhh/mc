package com.edu.ucenter.controller;

import com.edu.api.ucenter.UcenterControllerApi;
import com.edu.framework.domain.ucenter.McCompany;
import com.edu.framework.domain.ucenter.McMenu;
import com.edu.framework.domain.ucenter.McRole;
import com.edu.framework.domain.ucenter.McUser;
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
    public ResponseResult userBindPhone(@RequestParam String phone,@RequestParam String code) {
        return ucenterService.userBindPhone(phone, code);
    }

    @Override
    @PutMapping("/user/changepassword")
    public ResponseResult changePassword(@RequestParam String oldPassword,@RequestParam String newPassword) {
        return ucenterService.changePassword(oldPassword, newPassword);
    }

    @Override
    @DeleteMapping("/user/del/{username}")
    public ResponseResult deltUser(@PathVariable String username) {
        return ucenterService.deltUser(username);
    }

    @Override
    @GetMapping("/user/list/{page}/{size}")
    public CommonResponseResult findUserList(@PathVariable int page, @PathVariable int size, QueryMcUserRequest request) {
        return ucenterService.findList(page,size,request);
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
    public ResponseResult addRole(@RequestBody McRoleExt mcRoleExt) {
        return ucenterService.addRole(mcRoleExt);
    }

    @Override
    @PutMapping("/role/edit")
    public ResponseResult editRole(@RequestBody McRoleExt mcRoleExt) {
        return ucenterService.editRole(mcRoleExt);
    }

    @Override
    @DeleteMapping("/role/del/{id}")
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
    public CommonResponseResult findRoleList(@PathVariable int page,@PathVariable int size) {
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
    public CommonResponseResult findPermission() {
        return ucenterService.findPermission();
    }

    @Override
    @PostMapping("/permission/add")
    public ResponseResult addPerimission(@RequestBody McMenu mcMenu) {
        return ucenterService.addPerimission(mcMenu);
    }

    @Override
    @PutMapping("/permission/edit")
    public ResponseResult editPermission(@RequestBody McMenu mcMenu) {
        return ucenterService.editPermission(mcMenu);
    }

    @Override
    @DeleteMapping("/permission/del/{id}")
    public ResponseResult delPermission(@PathVariable String id) {
        return ucenterService.delPermission(id);
    }

    @Override
    @GetMapping("/company/list/{page}/{size}")
    public CommonResponseResult findCompanyList(@PathVariable int page,@PathVariable int size){
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
    @GetMapping("/company/user/list/{page}/{size}")
    public CommonResponseResult findCompanyUserList(@PathVariable int page,@PathVariable int size) {
        return ucenterService.findCompanyUserList(page, size);
    }

    @Override
    @PostMapping("/company/user/invite")
    public ResponseResult inviteUser(@RequestParam String username,@RequestParam String name) {
        return ucenterService.inviteUser(username, name);
    }

    @Override
    @DeleteMapping("/company/user/delete")
    public ResponseResult delCompanyUser(String username) {
        return ucenterService.delCompanyUser(username);
    }


}
