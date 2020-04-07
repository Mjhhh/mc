package com.edu.framework.domain.course.ext;

import com.edu.framework.domain.course.CourseQuestion;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class CourseQuestionExt extends CourseQuestion {
    String userpic;
    String username;
    Integer answerTotal;
    Integer lookNum;
}
