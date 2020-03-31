package com.edu.framework.domain.course;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name="course_evaluate")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class CourseEvaluate implements Serializable {
    private static final long serialVersionUID = -916357110051689886L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("课程主键")
    @Column(name="course_id")
    private String courseId;
    @ApiModelProperty("用户主键")
    @Column(name="user_id")
    private String userId;
    @ApiModelProperty("评论内容")
    private String content;
    @ApiModelProperty("评分")
    private Double score;
    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
