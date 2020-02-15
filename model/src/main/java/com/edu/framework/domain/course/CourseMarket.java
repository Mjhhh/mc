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
@Table(name="course_market")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CourseMarket implements Serializable {
    private static final long serialVersionUID = -916357110051689486L;
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    @ApiModelProperty("课程ID")
    private String id;

    @ApiModelProperty("收费规则，对应数据字典")
    private String charge;

    @ApiModelProperty("有效性，对应数据字典")
    private String valid;

    @ApiModelProperty("咨询QQ")
    private String qq;

    @ApiModelProperty("价格")
    private Float price;

    @ApiModelProperty("原价")
    private Float price_old;

    @ApiModelProperty("过期时间")
    private Date expires;

    @Column(name = "start_time")
    @ApiModelProperty("课程有效期-开始时间")
    private Date startTime;

    @Column(name = "end_time")
    @ApiModelProperty("课程有效期-结束时间")
    private Date endTime;


}
