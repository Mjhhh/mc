package com.edu.framework.domain.ucenter.ext;

import com.edu.framework.domain.ucenter.McRole;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class McRoleExt extends McRole {
    /**
     * 权限id
     */
    List<String> menuIds;
}
