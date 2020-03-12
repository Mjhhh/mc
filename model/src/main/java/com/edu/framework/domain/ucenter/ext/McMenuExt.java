package com.edu.framework.domain.ucenter.ext;

import com.edu.framework.domain.course.ext.CategoryNode;
import com.edu.framework.domain.ucenter.McMenu;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class McMenuExt extends McMenu {

    List<CategoryNode> children;
}
