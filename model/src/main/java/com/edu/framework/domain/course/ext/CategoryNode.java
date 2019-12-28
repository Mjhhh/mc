package com.edu.framework.domain.course.ext;

import com.edu.framework.domain.course.Category;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class CategoryNode extends Category {

    List<CategoryNode> children;

}
