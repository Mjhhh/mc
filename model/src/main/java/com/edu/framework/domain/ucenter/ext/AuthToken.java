package com.edu.framework.domain.ucenter.ext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
public class AuthToken {
    @ApiModelProperty("访问token")
    String jtl;
    @ApiModelProperty("刷新token")
    String refresh_token;
    @ApiModelProperty("jwt令牌")
    String access_token;
}
