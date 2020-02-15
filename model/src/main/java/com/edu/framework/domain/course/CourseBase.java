package com.edu.framework.domain.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
@Entity
@Table(name="course_base")
//@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class CourseBase implements Serializable {
    private static final long serialVersionUID = -916357110051689486L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("课程名称")
    private String name;
    @ApiModelProperty("适用人群")
    private String users;
    @ApiModelProperty("课程等级")
    private String mt;
    @ApiModelProperty("课程小分类")
    private String st;
    @ApiModelProperty("课程等级")
    private String grade;
    @ApiModelProperty("学习模式")
    private String studymodel;
    @ApiModelProperty("授课模式")
    private String teachmode;
    @ApiModelProperty("课程介绍")
    private String description;
    @ApiModelProperty("")
    private String status;
    @Column(name="company_id")
    @ApiModelProperty("")
    private String companyId;
    @Column(name="user_id")
    @ApiModelProperty("")
    private String userId;

}
