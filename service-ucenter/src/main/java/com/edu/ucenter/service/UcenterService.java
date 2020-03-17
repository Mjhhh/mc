package com.edu.ucenter.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.framework.domain.ucenter.*;
import com.edu.framework.domain.ucenter.ext.McRoleExt;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import com.edu.framework.domain.ucenter.request.QueryMcUserRequest;
import com.edu.framework.domain.ucenter.response.McUserResult;
import com.edu.framework.domain.ucenter.response.UcenterCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.utils.BCryptUtil;
import com.edu.ucenter.dao.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UcenterService {

    @Autowired
    McCompanyUserRepository mcCompanyUserRepository;

    @Autowired
    McUserRepository mcUserRepository;

    @Autowired
    McMenuMapper mcMenuMapper;

    @Autowired
    McUserMapper mcUserMapper;

    @Autowired
    McRoleRepository mcRoleRepository;

    @Autowired
    McMenuRepository mcMenuRepository;

    @Autowired
    McPermissionRepository mcPermissionRepository;

    @Autowired
    McUserRoleRepository mcUserRoleRepository;

    @Autowired
    McPermissionMapper mcPermissionMapper;

    /**
     * 获取角色
     * @param id
     * @return
     */
    private McRole getMcRole(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        Optional<McRole> optional = mcRoleRepository.findById(id);
        return optional.orElse(null);
    }

    private void delRoleUtil(String id) {
        McRole mcRole = this.getMcRole(id);
        if (mcRole == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        //删除所有角色权限映射
        mcPermissionRepository.deleteByRoleId(mcRole.getId());
        //删除所有角色用户映射
        mcUserRoleRepository.deleteByRoleId(mcRole.getId());
        //删除角色本身
        mcRoleRepository.delete(mcRole);
    }

    /**
     * 先删除角色权限映射，再重新导入
     */
    private void buildRolePermissionMap(McRoleExt mcRoleExt) {
        //如果权限id不为空且编辑的角色不是超级管理员，则导入
        if (mcRoleExt.getMenuIds() != null) {
            List<String> menuIds = mcRoleExt.getMenuIds();
            List<McPermission> mcPermissionList = new ArrayList<>();
            //删除所有角色权限映射
            if (mcRoleExt.getId() != null) {
                mcPermissionMapper.deleteByRoleId(mcRoleExt.getId());
            }
            for (String menuId : menuIds) {
                McPermission mcPermission = new McPermission();
                mcPermission.setMenuId(menuId);
                mcPermission.setRoleId(mcRoleExt.getId());
                mcPermission.setCreateTime(new Date());
                mcPermissionList.add(mcPermission);
            }
            mcPermissionRepository.saveAll(mcPermissionList);
        }
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    private McUser getMcUser(String username) {
        if (StringUtils.isBlank(username)) {
            ExceptionCast.cast(UcenterCode.UCENTER_USERNAME_NONE);
        }
        return mcUserRepository.findByUsername(username);
    }

    /**
     * 为简单权限列表分类
     * @param menuList
     * @param parentId
     * @return
     */
    private JSONArray findChildren(List<McMenu> menuList, String parentId) {
        JSONArray jsonArray = new JSONArray();
        for (McMenu menu : menuList) {
            if (StringUtils.equals(menu.getPId(), parentId)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", menu.getId());
                jsonObject.put("label", menu.getMenuName());
                if (menu.getLevel() < 3) {
                    JSONArray children = this.findChildren(menuList, menu.getId());
                    if (!children.isEmpty()) {
                        jsonObject.put("children", children);
                    }
                }
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 为权限列表分类
     * @param menuList
     * @param parentId
     * @return
     */
    private JSONArray findPermissionChildren(List<McMenu> menuList, String parentId) {
        JSONArray jsonArray = new JSONArray();
        for (McMenu menu : menuList) {
            if (StringUtils.equals(menu.getPId(), parentId)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", menu.getId());
                jsonObject.put("pid", menu.getPId());
                jsonObject.put("code", menu.getCode());
                jsonObject.put("menuName", menu.getMenuName());
                jsonObject.put("url", menu.getUrl());
                jsonObject.put("isMenu", menu.getIsMenu());
                if (menu.getLevel() == 1) {
                    jsonObject.put("level", "目录");
                } else if (menu.getLevel() == 2) {
                    jsonObject.put("level", "菜单");
                } else if (menu.getLevel() == 3) {
                    jsonObject.put("level", "权限");
                }
                jsonObject.put("sort", menu.getSort());
                jsonObject.put("status", menu.getStatus());
                jsonObject.put("icon", menu.getIcon());
                jsonObject.put("updateTime", menu.getUpdateTime());
                if (menu.getLevel() < 3) {
                    JSONArray children = this.findPermissionChildren(menuList, menu.getId());
                    if (!children.isEmpty()) {
                        jsonObject.put("children", children);
                    }
                }
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据用户名获取用户所有信息
     * @param username 用户名
     * @return
     */
    public McUserExt getUserext(String username) {
        McUser mcUser = this.getMcUser(username);
        if (mcUser == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        //检查账号是否被禁用
        if (StringUtils.equals(mcUser.getStatus(), "0")) {
            return null;
        }
        McUserExt mcUserExt = new McUserExt();
        BeanUtils.copyProperties(mcUser, mcUserExt);
        //用户id
        String userId = mcUserExt.getId();
        //查询用户所属公司
        McCompanyUser mcCompanyUser = mcCompanyUserRepository.findByUserId(userId);
//        if (mcCompanyUser != null) {
//            String companyId = mcCompanyUser.getCompanyId();
//            mcCompanyUser.setCompanyId(companyId);
//        }
        //查询用户的权限
        List<McMenu> mcMenus = mcMenuMapper.selectPermissionByUserId(userId);
        mcUserExt.setPermissions(mcMenus);
        return mcUserExt;
    }

    /**
     * 用户注册
     * @param mcUser
     * @return
     */
    @Transactional
    public McUserResult registered(McUser mcUser) {
        if (mcUser == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        if (StringUtils.isBlank(mcUser.getUsername()) || StringUtils.isBlank(mcUser.getPassword())) {
            ExceptionCast.cast(UcenterCode.UCENTER_USERNAME_OR_PASSWORD_NONE);
        }
        McUser one = this.getMcUser(mcUser.getUsername());
        if (one != null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_ALREADY_EXISTS);
        }
        McUser save = mcUserRepository.save(mcUser);
        return new McUserResult(CommonCode.SUCCESS, save);
    }

    /**
     * 编辑用户信息
     * @param mcUser
     * @return
     */
    @Transactional
    public ResponseResult editUser(McUser mcUser) {
        if (StringUtils.isBlank(mcUser.getUsername())) {
            ExceptionCast.cast(UcenterCode.UCENTER_USERNAME_IS_NULL);
        }
        McUser updateUser = this.getMcUser(mcUser.getUsername());
        if (updateUser == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        if (StringUtils.isNotBlank(mcUser.getName())) {
            updateUser.setName(mcUser.getName());
        }
        if (StringUtils.isNotBlank(mcUser.getUtype())) {
            updateUser.setUtype(mcUser.getUtype());
        }
        if (StringUtils.isNotBlank(mcUser.getBirthday())) {
            updateUser.setBirthday(mcUser.getBirthday());
        }
        if (StringUtils.isNotBlank(mcUser.getSex())) {
            updateUser.setSex(mcUser.getSex());
        }
        if (StringUtils.isNotBlank(mcUser.getEmail())) {
            updateUser.setEmail(mcUser.getEmail());
        }
        if (StringUtils.isNotBlank(mcUser.getPhone())) {
            updateUser.setPhone(mcUser.getPhone());
        }
        if (StringUtils.isNotBlank(mcUser.getQq())) {
            updateUser.setQq(mcUser.getQq());
        }
        if (StringUtils.isNotBlank(mcUser.getStatus())) {
            updateUser.setStatus(mcUser.getStatus());
        }
        updateUser.setUpdateTime(new Date());
        mcUserRepository.save(updateUser);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 查询列表
     * @param page
     * @param size
     * @param request
     * @return
     */
    public CommonResponseResult findList(int page, int size, QueryMcUserRequest request) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        Page<Object> startPage = PageHelper.startPage(page, size);
        List<McUser> list = mcUserMapper.findList(request);
        for (McUser mcUser : list) {
            if (StringUtils.isNotBlank(mcUser.getBirthday())) {
                mcUser.setBirthday(mcUser.getBirthday().replace("00:00:00.0",""));
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", Integer.parseInt(((Long)startPage.getTotal()).toString()));
        map.put("list", list);

        return new CommonResponseResult(CommonCode.SUCCESS,map);
    }

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    public McUser get(String username) {
        McUser mcUser = this.getMcUser(username);
        if (mcUser != null) {
            mcUser.setId(null);
            mcUser.setPassword(null);
        }
        return mcUser;
    }

    /**
     * 重置密码
     * @param username
     * @return
     */
    @Transactional
    public ResponseResult resetPassword(String username) {
        McUser mcUser = this.getMcUser(username);
        //默认密码为123456
        String initPassword = "123456";
        String encodePassword = BCryptUtil.encode(initPassword);
        mcUser.setPassword(encodePassword);
        mcUserRepository.save(mcUser);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加角色
     * @param mcRoleExt
     * @return
     */
    @Transactional
    public ResponseResult addRole(McRoleExt mcRoleExt) {
        if (mcRoleExt == null
                || StringUtils.isBlank(mcRoleExt.getRoleName())
                || StringUtils.isBlank(mcRoleExt.getRoleCode())) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        McRole mcRole = new McRole();
        BeanUtils.copyProperties(mcRoleExt,mcRole);
        mcRole.setCreateTime(new Date());
        //启用状态
        mcRole.setStatus("1");
        //保存角色信息
        McRole save = mcRoleRepository.save(mcRole);
        //建立角色权限映射
        mcRoleExt.setId(save.getId());
        this.buildRolePermissionMap(mcRoleExt);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 编辑角色
     * @param mcRoleExt
     * @return
     */
    @Transactional
    public ResponseResult editRole(McRoleExt mcRoleExt) {
        if (mcRoleExt == null || StringUtils.isBlank(mcRoleExt.getId())) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        McRole updateRole = this.getMcRole(mcRoleExt.getId());
        if (updateRole == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ROLE_ALREADY_EXISTS);
        }
        if (StringUtils.isNotBlank(mcRoleExt.getRoleName())) {
            updateRole.setRoleName(mcRoleExt.getRoleName());
        }
        if (StringUtils.isNotBlank(mcRoleExt.getRoleCode())) {
            updateRole.setRoleCode(mcRoleExt.getRoleCode());
        }
        if (StringUtils.isNotBlank(mcRoleExt.getDescription())) {
            updateRole.setDescription(mcRoleExt.getDescription());
        }
        if (StringUtils.isNotBlank(mcRoleExt.getStatus())) {
            updateRole.setStatus(mcRoleExt.getStatus());
        }
        updateRole.setUpdateTime(new Date());
        mcRoleRepository.save(updateRole);
        //建立角色权限映射
        this.buildRolePermissionMap(mcRoleExt);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Transactional
    public ResponseResult delRole(String id) {
        this.delRoleUtil(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除角色列表
     * @param ids
     * @return
     */
    @Transactional
    public ResponseResult delRoleList(List<String> ids) {
        ids.forEach(this::delRoleUtil);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取角色列表
     * @return
     */
    public CommonResponseResult findRoleList(int page, int size) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        Page<Object> startPage = PageHelper.startPage(page, size);
        List<McRole> list = mcRoleRepository.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("total", startPage.getTotal());
        result.put("list", list);
        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }

    /**
     * 获取权限精简列表
     * @return
     */
    public CommonResponseResult findPermissionSimpleList() {
        //查找未被禁用的权限列表
        List<McMenu> menuList = mcMenuMapper.selectPermissionByStatus("1");
        JSONArray jsonArray = this.findChildren(menuList, "0");
        return new CommonResponseResult(CommonCode.SUCCESS, jsonArray);
    }

    /**
     * 获取角色权限精简列表
     * @param roleId
     * @return
     */
    public CommonResponseResult findPermissionSimpleListByRole(String roleId) {
        //获取未被禁用的权限
        List<McMenu> mcMenus = mcMenuMapper.selectPermissionByStatusAndRoleId("1", roleId);
        List<String> menuIds = new ArrayList<>();
        if (!mcMenus.isEmpty()) {
            for (McMenu menu : mcMenus) {
                menuIds.add(menu.getId());
            }
        }
        return new CommonResponseResult(CommonCode.SUCCESS, menuIds);
    }

    /**
     * 获取权限信息
     * @return
     */
    public CommonResponseResult findPermission() {
        List<McMenu> mcMenuList = mcMenuRepository.findAll();
        JSONArray result = new JSONArray();
        if (mcMenuList != null) {
            result = this.findPermissionChildren(mcMenuList, "0");
        }
        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }

    /**
     * 添加权限
     * @param mcMenu
     * @return
     */
    @Transactional
    public ResponseResult addPerimission(McMenu mcMenu) {
        if (mcMenu == null
                || StringUtils.isBlank(mcMenu.getCode())
                || StringUtils.isBlank(mcMenu.getMenuName())
                || mcMenu.getLevel() == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        if (StringUtils.isBlank(mcMenu.getPId())) {
            //设置为父节点为根目录
            mcMenu.setPId("0");
        }
        //如果是权限，则不是菜单
        if (StringUtils.equals(mcMenu.getIsMenu(), "3")) {
            mcMenu.setIsMenu("0");
        } else{
            mcMenu.setIsMenu("1");
        }
        //默认启用
        mcMenu.setStatus("1");
        mcMenu.setId("");
        mcMenu.setCreateTime(new Date());
        mcMenuRepository.save(mcMenu);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
