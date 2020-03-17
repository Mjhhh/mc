package com.edu.framework.domain.ucenter.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
public class UserTokenStore extends AuthToken {
    /**
     * 用户id
     */
    String userId;
    /**
     * 用户类型
     */
    String utype;
    /**
     * 用户所属企业信息
     */
    String companyId;
}
