package com.edu.ucenter.service;

import com.edu.framework.domain.ucenter.McCompanyUser;
import com.edu.framework.domain.ucenter.McMenu;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import com.edu.framework.domain.ucenter.response.UcenterCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.ucenter.dao.McCompanyUserRepository;
import com.edu.ucenter.dao.McMenuMapper;
import com.edu.ucenter.dao.McUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UcenterService {

    @Autowired
    McCompanyUserRepository mcCompanyUserRepository;

    @Autowired
    McUserRepository mcUserRepository;

    @Autowired
    McMenuMapper mcMenuMapper;

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
     * 根据用户名获取用户所有信息
     * @param username 用户名
     * @return
     */
    public McUserExt getUserext(String username) {
        McUser mcUser = this.getMcUser(username);
        if (mcUser == null) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        McUserExt mcUserExt = new McUserExt();
        BeanUtils.copyProperties(mcUser, mcUserExt);
        //用户id
        String userId = mcUserExt.getId();
        //查询用户所属公司
        McCompanyUser mcCompanyUser = mcCompanyUserRepository.findByUserId(userId);
        if (mcCompanyUser != null) {
            String companyId = mcCompanyUser.getCompanyId();
            mcCompanyUser.setCompanyId(companyId);
        }
        //查询用户的权限
        List<McMenu> mcMenus = mcMenuMapper.selectPermissionByUserId(userId);
        mcUserExt.setPermissions(mcMenus);
        return mcUserExt;
    }
}
