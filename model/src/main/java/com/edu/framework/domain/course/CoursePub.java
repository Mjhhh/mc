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
 */
@Data
@ToString
@Entity
@Table(name="course_pub")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CoursePub implements Serializable {
    private static final long serialVersionUID = -916357110051689487L;
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("课程名称")
    private String name;
    @ApiModelProperty("适用人群")
    private String users;
    @ApiModelProperty("大分类")
    private String mt;
    @ApiModelProperty("小分类")
    private String st;
    @ApiModelProperty("课程等级")
    private String grade;
    @ApiModelProperty("学习模式")
    private String studymodel;
    @ApiModelProperty("教育模式")
    private String teachmode;
    @ApiModelProperty("课程介绍")
    private String description;
    @ApiModelProperty("图片")
    private String pic;
    @ApiModelProperty("时间戳logstash使用")
    private Date timestamp;
    @ApiModelProperty("收费规则，对应数据字典")
    private String charge;
    @ApiModelProperty("有效性，对应数据字典")
    private String valid;
    @ApiModelProperty("咨询qq")
    private String qq;
    @ApiModelProperty("价格")
    private Double price;
    @ApiModelProperty("原价格")
    private Double price_old;
    @ApiModelProperty("过期时间")
    private String expires;
    @ApiModelProperty("课程计划")
    private String teachplan;
    @Column(name="pub_time")
    @ApiModelProperty("课程发布时间")
    private String pubTime;
}
