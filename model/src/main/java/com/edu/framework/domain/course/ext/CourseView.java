package com.edu.framework.domain.course.ext;

import com.edu.framework.domain.course.CourseBase;
import com.edu.framework.domain.course.CourseMarket;
import com.edu.framework.domain.course.CoursePic;
import com.edu.framework.domain.course.CourseTeacher;
import com.edu.framework.domain.ucenter.McCompany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
public class CourseView implements Serializable {
    /**
     * 基础信息
     */
    CourseBase courseBase;
    /**
     * 课程营销
     */
    CourseMarket courseMarket;
    /**
     * 课程图片
     */
    CoursePic coursePic;
    /**
     * 教学计划
     */
    TeachplanNode teachplanNode;
    /**
     * 讲师资料
     */
    CourseTeacher courseTeacher;
    /**
     * 组织资料
     */
    McCompany mcCompany;
}
