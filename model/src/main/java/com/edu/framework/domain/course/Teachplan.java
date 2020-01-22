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
@Entity
@Table(name = "teachplan")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Teachplan implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("课程名称")
    private String pname;

    @ApiModelProperty("课程上级节点")
    private String parentid;

    @ApiModelProperty("层级")
    private String grade;

    @ApiModelProperty("课程类型:1视频、2文档")
    private String ptype;

    @ApiModelProperty("章节及课程时介绍")
    private String description;

    @ApiModelProperty("时长，单位分钟")
    private Double timelength;

    @ApiModelProperty("课程id")
    private String courseid;

    @ApiModelProperty("排序字段")
    private Integer orderby;

    @ApiModelProperty("状态：0-未发布、1-已发布")
    private String status;

    @ApiModelProperty("是否试学")
    private String trylearn;

    /**
     * 层级
     */
    public static final String GRADE_LEVEL_ONE = "1";
    public static final String GRADE_LEVEL_TWO = "2";
    public static final String GRADE_LEVEL_THREE = "3";

    /**
     * 发布状态
     */
    public static final String STATUS_NOT_RELEASE = "0";
    public static final String STATUS_RELEASE = "1";
}
