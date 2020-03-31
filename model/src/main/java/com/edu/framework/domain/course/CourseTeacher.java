package com.edu.framework.domain.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mjh on 2019-11-11
 * @author Admin
 */
@Data
@ToString
@Entity
@Table(name="course_teacher")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CourseTeacher implements Serializable {
    private static final long serialVersionUID = -916357110051689684L;
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    @ApiModelProperty("课程ID")
    private String id;

    @ApiModelProperty("讲师名称")
    private String name;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("简介")
    private String description;

}
