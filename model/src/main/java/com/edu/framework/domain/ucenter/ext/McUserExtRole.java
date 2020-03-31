package com.edu.framework.domain.ucenter.ext;

import com.edu.framework.domain.ucenter.McUser;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class McUserExtRole extends McUser {

    private List<String> roleNames;

    private List<String> roleIds;
}
