package com.edu.framework.domain.portalview;

import com.edu.framework.domain.course.CourseMarket;
import com.edu.framework.domain.course.ext.TeachplanNode;
import com.edu.framework.domain.course.CourseBase;
import com.edu.framework.domain.course.CoursePic;
import com.edu.framework.domain.report.ReportCourse;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
@Document(collection = "pre_view_course")
public class PreViewCourse implements Serializable{

    @Id
    private String id;
    private CourseBase courseBase;
    private CourseMarket courseMarket;
    private CoursePic coursePic;
    private TeachplanNode teachplan;
    //课程统计信息
    private ReportCourse reportCourse;

}
