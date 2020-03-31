package com.edu.framework.domain.course.ext;

import com.edu.framework.domain.course.CourseEvaluate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class CourseEvaluateExt extends CourseEvaluate {
    String userpic;
    String username;
}
