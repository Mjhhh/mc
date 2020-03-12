package com.edu.framework.domain.ucenter.ext;

import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.McMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class McUserExt extends McUser {

    @ApiModelProperty("权限信息")
    private List<McMenu> permissions;

    @ApiModelProperty("企业信息")
    private String companyId;
}
