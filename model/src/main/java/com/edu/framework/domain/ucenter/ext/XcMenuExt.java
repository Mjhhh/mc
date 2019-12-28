package com.edu.framework.domain.ucenter.ext;

import com.edu.framework.domain.course.ext.CategoryNode;
import com.edu.framework.domain.ucenter.XcMenu;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class XcMenuExt extends XcMenu {

    List<CategoryNode> children;
}
