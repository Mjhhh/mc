package com.edu.auth.service;

import com.edu.auth.client.UserClient;
import com.edu.framework.domain.ucenter.McMenu;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserClient userClient;

    @Autowired
    ClientDetailsService clientDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                //密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        //远程调用用户中心根据账号查询用户信息
        McUserExt userext = userClient.getUserext(username);
        if (userext == null) {
            //返回空给spring security表示用户不存在
            return null;
        }
        //取出正确密码（hash值）
        String password = userext.getPassword();
        //从数据库获取权限
        List<McMenu> permissions = userext.getPermissions();
        List<String> userPermission = new ArrayList<>();
        permissions.forEach(item -> userPermission.add(item.getCode()));

        String userPermissionString = StringUtils.join(userPermission.toArray(), ",");
        UserJwt userDetails = new UserJwt(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(userPermissionString));
        userDetails.setId(userext.getId());
        //用户类型
        userDetails.setUtype(userext.getUtype());
        //所属企业
        userDetails.setCompanyId(userext.getCompanyId());
        //用户名称
        userDetails.setName(userext.getName());
        //用户头像
        userDetails.setUserpic(userext.getUserpic());
        return userDetails;
    }

}
