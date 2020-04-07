package com.edu.framework.domain.course.ext;

import com.edu.framework.domain.course.CourseAnswer;
import com.edu.framework.domain.course.CourseEvaluate;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class CourseAnswerExt extends CourseAnswer {
    String userpic;
    String username;
}
