package com.edu.ucenter.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.framework.domain.message.McMessage;
import com.edu.framework.domain.ucenter.*;
import com.edu.framework.domain.ucenter.ext.McRoleExt;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import com.edu.framework.domain.ucenter.ext.McUserExtRole;
import com.edu.framework.domain.ucenter.request.QueryMcUserRequest;
import com.edu.framework.domain.ucenter.response.McCompanyResult;
import com.edu.framework.domain.ucenter.response.McUserResult;
import com.edu.framework.domain.ucenter.response.UcenterCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.utils.BCryptUtil;
import com.edu.framework.utils.McOauth2Util;
import com.edu.ucenter.client.MessageClient;
import com.edu.ucenter.dao.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
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

    @Autowired
    McRoleMapper mcRoleMapper;

    @Autowired
    McCompanyRepository mcCompanyRepository;

    @Autowired
    McCompanyMapper mcCompanyMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    MessageClient messageClient;

    /**
     * 获取HttpServletRequest
     * @return
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取权限
     *
     * @param id
     * @return
     */
    private McMenu getMcMenu(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        Optional<McMenu> optional = mcMenuRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 获取用户id
     * @return
     */
    private String getUserId() {
        HttpServletRequest request = this.getRequest();
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (StringUtils.isBlank(userJwt.getId())) {
            ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        }
        return userJwt.getId();
    }

    private McUser getMcUserById(String userId) {
        if (StringUtils.isBlank(userId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Optional<McUser> optional = mcUserRepository.findById(userId);
        return optional.orElse(null);
    }

    /**
     * 获取公司
     *
     * @param id
     * @return
     */
    private McCompany getMcCompany(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        Optional<McCompany> optional = mcCompanyRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 获取角色
     *
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

    /**
     * 获取用户角色映射
     *
     * @param id
     * @return
     */
    private McUserRole getMcUserRole(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        Optional<McUserRole> optional = mcUserRoleRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 删除角色工具
     *
     * @param id
     */
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
     * 改变权限的状态
     */
    private void changeMenuStatus(String id, String status) {
        McMenu mcMenu = this.getMcMenu(id);
        if (mcMenu == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_PERMISSION_NOT_EXISTS);
        }
        if (mcMenu.getLevel() != 3) {
            List<McMenu> mcMenuList = new ArrayList<>();
            mcMenuList = this.findChildrenMenu(mcMenuList, id);
            for (McMenu menu : mcMenuList) {
                menu.setStatus(status);
                mcMenuRepository.save(menu);
            }
        }
    }

    /**
     * 删除权限
     */
    private void delMenuUtil(String id) {
        McMenu mcMenu = this.getMcMenu(id);
        if (mcMenu == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_PERMISSION_NOT_EXISTS);
        }
        if (mcMenu.getLevel() != 3) {
            List<McMenu> mcMenuList = new ArrayList<>();
            mcMenuList = this.findChildrenMenu(mcMenuList, id);
            mcMenuList.add(mcMenu);
            for (McMenu menu : mcMenuList) {
                List<McPermission> mcPermissionList = mcPermissionRepository.findByMenuId(menu.getId());
                if (!mcPermissionList.isEmpty()) {
                    mcPermissionRepository.deleteByMenuId(menu.getId());
                }
            }
            mcMenuRepository.deleteAll(mcMenuList);
        } else {
            mcMenuRepository.delete(mcMenu);
        }
    }

    /**
     * 遍历所有下级权限
     *
     * @param mcMenuList
     * @param pid
     * @return
     */
    private List<McMenu> findChildrenMenu(List<McMenu> mcMenuList, String pid) {
        List<McMenu> childrenMenus = mcMenuRepository.findByPId(pid);
        mcMenuList.addAll(childrenMenus);
        for (McMenu mcMenu : childrenMenus) {
            if (mcMenu.getLevel() != 3) {
                this.findChildrenMenu(mcMenuList, mcMenu.getId());
            }
        }
        return mcMenuList;
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
     *
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
     *
     * @param menuList
     * @param parentId
     * @return
     */
    private JSONArray findPermissionChildren(List<McMenu> menuList, String parentId) {
        JSONArray jsonArray = new JSONArray();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                if (menu.getCreateTime() != null) {
                    jsonObject.put("createTime", dateFormat.format(menu.getCreateTime()));
                }
                if (menu.getUpdateTime() != null) {
                    jsonObject.put("updateTime", dateFormat.format(menu.getUpdateTime()));
                }
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
     *
     * @param username 用户名
     * @return
     */
    public McUserExt getUserext(String username) {
        McUser mcUser = this.getMcUser(username);
        if (mcUser == null) {
            return null;
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
        if (mcCompanyUser != null) {
            String companyId = mcCompanyUser.getCompanyId();
            mcUserExt.setCompanyId(companyId);
        }
        //查询用户的权限
        List<McMenu> mcMenus = mcMenuMapper.selectPermissionByUserId(userId);
        mcUserExt.setPermissions(mcMenus);
        return mcUserExt;
    }

    /**
     * 用户注册
     *
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
     *
     * @param mcUserExtRole
     * @return
     */
    @Transactional
    public ResponseResult editUser(McUserExtRole mcUserExtRole) {
        if (StringUtils.isBlank(mcUserExtRole.getUsername())) {
            ExceptionCast.cast(UcenterCode.UCENTER_USERNAME_IS_NULL);
        }
        McUser updateUser = this.getMcUser(mcUserExtRole.getUsername());
        if (updateUser == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        if (StringUtils.isNotBlank(mcUserExtRole.getName())) {
            updateUser.setName(mcUserExtRole.getName());
        }
        if (StringUtils.isNotBlank(mcUserExtRole.getUtype())) {
            updateUser.setUtype(mcUserExtRole.getUtype());
        }
        if (StringUtils.isNotBlank(mcUserExtRole.getBirthday())) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(new Date(Long.parseLong(mcUserExtRole.getBirthday())));
            updateUser.setBirthday(date);
        }
        if (StringUtils.isNotBlank(mcUserExtRole.getSex())) {
            updateUser.setSex(mcUserExtRole.getSex());
        }
        if (StringUtils.isNotBlank(mcUserExtRole.getEmail())) {
            updateUser.setEmail(mcUserExtRole.getEmail());
        }
        if (StringUtils.isNotBlank(mcUserExtRole.getPhone())) {
            updateUser.setPhone(mcUserExtRole.getPhone());
        }
        if (StringUtils.isNotBlank(mcUserExtRole.getQq())) {
            updateUser.setQq(mcUserExtRole.getQq());
        }
        if (StringUtils.isNotBlank(mcUserExtRole.getStatus())) {
            updateUser.setStatus(mcUserExtRole.getStatus());
        }
        if (StringUtils.isNotBlank(mcUserExtRole.getUserpic())) {
            updateUser.setUserpic(mcUserExtRole.getUserpic());
        }
        if (!CollectionUtils.isEmpty(mcUserExtRole.getRoleIds())) {
            List<String> roleIds = mcUserExtRole.getRoleIds();
            //先删除所有角色
            mcUserRoleRepository.deleteByUserId(mcUserExtRole.getId());
            for (String roleId : roleIds) {
                McRole mcRole = this.getMcRole(roleId);
                if (mcRole != null) {
                    McUserRole mcUserRole = new McUserRole();
                    mcUserRole.setCreateTime(new Date());
                    mcUserRole.setRoleId(mcRole.getId());
                    mcUserRole.setUserId(mcUserExtRole.getId());

                    mcUserRoleRepository.save(mcUserRole);
                }
            }
        }
        updateUser.setUpdateTime(new Date());
        mcUserRepository.save(updateUser);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除用户账号
     *
     * @param username
     * @return
     */
    @Transactional
    public ResponseResult deltUser(String username) {
        if (StringUtils.isBlank(username)) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        McUser mcUser = this.getMcUser(username);
        if (mcUser == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        List<McUserRole> mcUserRoleList = mcUserRoleRepository.findByUserId(mcUser.getId());
        mcUserRoleRepository.deleteAll(mcUserRoleList);
        mcUserRepository.delete(mcUser);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 查询用户列表
     *
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
        List<McUser> mcUserList = mcUserMapper.selectList(request);
        List<McUserExtRole> mcUserExtRoles = new ArrayList<>();
        for (McUser mcUser : mcUserList) {
            McUserExtRole mcUserExtRole = new McUserExtRole();
            BeanUtils.copyProperties(mcUser, mcUserExtRole);
            List<McRole> mcRoleList = mcRoleMapper.selectRoleByUserId(mcUser.getId());
            List<String> roleIds = new ArrayList<>();
            List<String> roleNames = new ArrayList<>();
            for (McRole mcRole : mcRoleList) {
                roleIds.add(mcRole.getId());
                roleNames.add(mcRole.getRoleName());
            }
            mcUserExtRole.setRoleIds(roleIds);
            mcUserExtRole.setRoleNames(roleNames);
            mcUserExtRoles.add(mcUserExtRole);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", Integer.parseInt(((Long) startPage.getTotal()).toString()));
        map.put("list", mcUserExtRoles);

        return new CommonResponseResult(CommonCode.SUCCESS, map);
    }

    /**
     * 获取用户信息
     *
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
     *
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
        mcUser.setSalt(initPassword);
        mcUserRepository.save(mcUser);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加角色
     *
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
        BeanUtils.copyProperties(mcRoleExt, mcRole);
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
        if (mcMenu.getLevel() == 3) {
            mcMenu.setIsMenu("0");
        } else {
            mcMenu.setIsMenu("1");
        }
        //默认启用
        mcMenu.setStatus("1");
        mcMenu.setId("");
        mcMenu.setCreateTime(new Date());
        mcMenu.setUpdateTime(new Date());
        mcMenuRepository.save(mcMenu);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 编辑权限
     *
     * @param mcMenu
     * @return
     */
    @Transactional
    public ResponseResult editPermission(McMenu mcMenu) {
        if (mcMenu == null || StringUtils.isBlank(mcMenu.getId())) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        McMenu update = this.getMcMenu(mcMenu.getId());
        if (update == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        if (StringUtils.isNotBlank(mcMenu.getCode())) {
            update.setCode(mcMenu.getCode());
        }
        if (StringUtils.isNotBlank(mcMenu.getMenuName())) {
            update.setMenuName(mcMenu.getMenuName());
        }
        if (mcMenu.getLevel() != null) {
            update.setLevel(mcMenu.getLevel());
        }
        if (StringUtils.isNotBlank(mcMenu.getPId())) {
            update.setPId(mcMenu.getPId());
        }
        //如果是权限，则不是菜单
        if (mcMenu.getLevel() != null) {
            if (mcMenu.getLevel() == 3) {
                mcMenu.setIsMenu("0");
            } else {
                mcMenu.setIsMenu("1");
            }
        }
        if (StringUtils.isNotBlank(mcMenu.getStatus()) && !StringUtils.equals(update.getStatus(), mcMenu.getStatus())) {
            update.setStatus(mcMenu.getStatus());
            this.changeMenuStatus(mcMenu.getId(), mcMenu.getStatus());
        }
        update.setUpdateTime(new Date());
        mcMenuRepository.save(update);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @Transactional
    public ResponseResult delPermission(String id) {
        this.delMenuUtil(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加组织
     *
     * @param mcCompany
     * @return
     */
    @Transactional
    public ResponseResult addCompany(McCompany mcCompany) {
        if (mcCompany == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_COMPANY_NOT_EXISTS);
        }
        HttpServletRequest request = this.getRequest();
        //调用工具类取出用户信息
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (StringUtils.isBlank(userJwt.getId())) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        if (StringUtils.isBlank(userJwt.getCompanyId())) {
            mcCompany.setId(null);
        } else {
            mcCompany.setId(userJwt.getCompanyId());
        }
        //禁用状态
        mcCompany.setStatus("0");
        String userId = this.getUserId();
        McCompany isExists = mcCompanyRepository.findByUserId(userId);
        if (isExists != null) {
            mcCompany.setId(isExists.getId());
        }
        mcCompany.setUserId(userId);
        McCompany saveMcCompany = mcCompanyRepository.save(mcCompany);

        //设置用户公司映射
        String userid = userJwt.getId();
        McCompanyUser mcCompanyUser = mcCompanyUserRepository.findByUserId(userid);
        if (mcCompanyUser == null) {
            mcCompanyUser = new McCompanyUser();
        }
        mcCompanyUser.setCompanyId(saveMcCompany.getId());
        mcCompanyUser.setUserId(userid);
        //默认禁用
        mcCompanyUser.setStatus("0");
        mcCompanyUserRepository.save(mcCompanyUser);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 编辑组织
     *
     * @param mcCompany
     * @return
     */
    public ResponseResult editCompany(McCompany mcCompany) {
        if (mcCompany == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_COMPANY_NOT_EXISTS);
        }
        McCompany update = this.getMcCompany(mcCompany.getId());
        if (update == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_COMPANY_NOT_EXISTS);
        }
        if (StringUtils.isNotBlank(mcCompany.getName())) {
            update.setName(mcCompany.getName());
        }
        if (StringUtils.isNotBlank(mcCompany.getLinkname())) {
            update.setLinkname(mcCompany.getLinkname());
        }
        if (StringUtils.isNotBlank(mcCompany.getMobile())) {
            update.setMobile(mcCompany.getMobile());
        }
        if (StringUtils.isNotBlank(mcCompany.getEmail())) {
            update.setEmail(mcCompany.getEmail());
        }
        if (StringUtils.isNotBlank(mcCompany.getIntro())) {
            update.setIntro(mcCompany.getIntro());
        }
        if (StringUtils.isNotBlank(mcCompany.getStatus())) {
            if (StringUtils.equals(mcCompany.getStatus(), "1")) {
                McCompanyUser mcCompanyUser = mcCompanyUserRepository.findByUserId(update.getUserId());
                mcCompanyUser.setStatus("1");
                mcCompanyUserRepository.save(mcCompanyUser);
            }
            update.setStatus(mcCompany.getStatus());
        }
        mcCompanyRepository.save(update);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 组织列表
     *
     * @param page
     * @param size
     * @return
     */
    public CommonResponseResult findCompanyList(int page, int size) {
        page = page - 1;
        if (page < 0) {
            page = 0;
        }
        if (size < 0) {
            size = 10;
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        org.springframework.data.domain.Page<McCompany> all = mcCompanyRepository.findAll(pageRequest);
        long total = all.getTotalElements();
        List<McCompany> mcCompanyList = all.getContent();
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("mcCompanyList", mcCompanyList);
        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }

    /**
     * 获取公司信息
     * @param companyId
     * @return
     */
    public McCompanyResult getCompany(String companyId) {
        McCompany mcCompany = this.getMcCompany(companyId);
        return new McCompanyResult(CommonCode.SUCCESS, mcCompany);
    }

    /**
     * 获取组织人员信息
     * @return
     */
    public CommonResponseResult findCompanyUserList(int page, int size) {
        Page<Object> startPage = PageHelper.startPage(page, size);
        String userId = this.getUserId();
        McCompany mcCompany = mcCompanyRepository.findByUserIdAndStatus(userId, "1");
        if (mcCompany == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_COMPANY_NOT_EXISTS);
        }
        //获取组织人员列表
        List<McUser> mcUserList = mcCompanyMapper.selectMcUserByCompanyId(mcCompany.getId());
        JSONObject result = new JSONObject();
        result.put("total", startPage.getTotal());
        result.put("list", mcUserList);
        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }

    /**
     * 邀请用户加入组织
     * @param username
     * @param name
     * @return
     */
    @Transactional
    public ResponseResult inviteUser(String username, String name) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(name)) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        McUser mcUser = mcUserRepository.findByUsernameAndName(username, name);
        if (mcUser == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        McCompanyUser isExists = mcCompanyUserRepository.findByUserIdAndStatus(mcUser.getId(), "1");
        if (isExists != null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ALREADY_HAVE_COMPANY);
        }
        HttpServletRequest request = this.getRequest();
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (userJwt == null) {
            ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        }
        String companyId = userJwt.getCompanyId();
        McCompanyUser mcCompanyUser = new McCompanyUser();
        mcCompanyUser.setCompanyId(companyId);
        mcCompanyUser.setStatus("0");
        mcCompanyUser.setUserId(mcUser.getId());
        mcCompanyUserRepository.save(mcCompanyUser);

        String userId = this.getUserId();
        McUser inviter = this.getMcUserById(userId);

        McMessage mcMessage = new McMessage();
        mcMessage.setContent(inviter.getName() + "邀请您加入组织！是否接受？");
        mcMessage.setReceiver(mcUser.getId());
        mcMessage.setType("1");
        messageClient.addMsg(mcMessage);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 移除组织用户
     * @param username
     * @return
     */
    public ResponseResult delCompanyUser(String username) {
        McUser mcUser = this.getMcUser(username);
        if (mcUser == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        HttpServletRequest request = this.getRequest();
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (userJwt == null) {
            ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        }
        McCompanyUser mcCompanyUser = mcCompanyUserRepository.findByUserIdAndCompanyId(mcUser.getId(), userJwt.getCompanyId());
        if (mcCompanyUser == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        mcCompanyUserRepository.delete(mcCompanyUser);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取用户信息
     * @param userid
     * @return
     */
    public McUser getById(String userid) {
        if (StringUtils.isBlank(userid)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Optional<McUser> optional = mcUserRepository.findById(userid);
        if (!optional.isPresent()) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        McUser mcUser = optional.get();
        mcUser.setPassword(null);
        return mcUser;
    }

    /**
     * 绑定用户手机号码
     * @param phone
     * @param code
     * @return
     */
    public ResponseResult userBindPhone(String phone, String code) {
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        McUser findUser = mcUserRepository.findByPhone(phone);
        if (findUser != null) {
            ExceptionCast.cast(UcenterCode.PHONE_IS_ALREADY_EXISTS);
        }
        String redisCode = stringRedisTemplate.opsForValue().get(phone);
        if (!StringUtils.equals(code, redisCode)) {
            ExceptionCast.cast(UcenterCode.VERIFICATION_CODE_ERROR);
        }
        String userId = this.getUserId();
        McUser mcUser = this.getMcUserById(userId);
        mcUser.setPhone(phone);
        mcUserRepository.save(mcUser);
        return ResponseResult.SUCCESS();
    }

    /**
     * 根据手机号码获取用户
     * @param phone
     * @return
     */
    public McUser getByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        McUser mcuser = mcUserRepository.findByPhone(phone);
        return mcuser;
    }

    /**
     * 根据ids查询用户
     * @param ids
     * @return
     */
    public List<McUser> findUserListByIds(List<String> ids) {
        List<McUser> mcUserList = mcUserMapper.selectListByIds(ids);
        return mcUserList;
    }

    /**
     * 查询用户名和用户主键列表
     * @return
     */
    public CommonResponseResult findUserIdAndUserNameList() {
        List<McUser> mcUserList = mcUserRepository.findAll();
        JSONArray array = new JSONArray();
        for (McUser mcUser : mcUserList) {
            JSONObject object = new JSONObject();
            object.put("username", mcUser.getName());
            object.put("userid", mcUser.getId());

            array.add(object);
        }
        return new CommonResponseResult(CommonCode.SUCCESS, array);
    }

    /**
     * 用户修改密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public ResponseResult changePassword(String oldPassword, String newPassword) {
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        String userId = this.getUserId();
        McUser mcUser = this.getMcUserById(userId);
        if (mcUser == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        //校验旧密码
        boolean isEquals = BCryptUtil.matches(oldPassword, mcUser.getPassword());
        if (!isEquals) {
            ExceptionCast.cast(UcenterCode.UCENTER_PASSWORD_ERROR);
        }
        String encodeNewPassword = BCryptUtil.encode(newPassword);
        mcUser.setPassword(encodeNewPassword);
        mcUser.setSalt(newPassword);
        mcUserRepository.save(mcUser);
        return ResponseResult.SUCCESS();
    }

    /**
     * 获取邀请记录
     * @param userId
     * @return
     */
    public McCompanyUser getCompanyUser(String userId) {
        if (StringUtils.isBlank(userId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        McCompanyUser mcCompanyUser = mcCompanyUserRepository.findByUserId(userId);
        return mcCompanyUser;
    }

    /**
     * 根据用户id从组织中移除用户
     * @param userId
     * @return
     */
    @Transactional
    public ResponseResult delCompanyUserByUserId(String userId) {
        mcCompanyUserRepository.deleteByUserId(userId);
        return ResponseResult.SUCCESS();
    }

    /**
     * 接受邀请
     * @param userId
     * @return
     */
    public ResponseResult accpetInvite(String userId) {
        if (StringUtils.isBlank(userId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        McCompanyUser companyUser = this.getCompanyUser(userId);
        companyUser.setStatus("1");
        mcCompanyUserRepository.save(companyUser);
        return ResponseResult.SUCCESS();
    }

    /**
     * 获取组织个人信息
     * @return
     */
    public CommonResponseResult findCompanyUser() {
        HttpServletRequest request = this.getRequest();
        //调用工具类取出用户信息
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (StringUtils.isBlank(userJwt.getCompanyId())) {
            ExceptionCast.cast(UcenterCode.UCENTER_PARAMS_IS_NULL);
        }
        String companyId = userJwt.getCompanyId();
        McCompany mcCompany = mcCompanyRepository.findByIdAndStatus(companyId, "1");
        if (mcCompany == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_COMPANY_NOT_EXISTS);
        }
        //获取组织人员列表
        String userId = this.getUserId();
        McUser mcUser = this.getMcUserById(userId);
        List<McUser> mcUserList = new ArrayList<>();
        mcUserList.add(mcUser);
        JSONObject result = new JSONObject();
        result.put("list", mcUserList);
        return new CommonResponseResult(CommonCode.SUCCESS, result);

    }

    /**
     * 通过昵称查找用户
     * @param nickname
     * @return
     */
    public McUser getByName(String nickname) {
        McUser mcUser = this.mcUserRepository.findByName(nickname);
        if (mcUser != null) {
            mcUser.setPassword(null);
        }
        return mcUser;

    }

    /**
     * 更具用
     * @param userId
     * @return
     */
    public McCompanyResult getCompanyByUser(String userId) {
        McCompany mcCompany = mcCompanyRepository.findByUserId(userId);
        return new McCompanyResult(CommonCode.SUCCESS, mcCompany);
    }
}
