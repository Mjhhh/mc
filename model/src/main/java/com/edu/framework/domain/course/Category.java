package com.edu.framework.domain.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 */
@Data
@ToString
@Entity
@Table(name="category")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class Category implements Serializable {
    private static final long serialVersionUID = -906357110051689484L;

    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类标签")
    private String label;

    @ApiModelProperty("父结点id")
    private String parentid;

    @ApiModelProperty("是否显示")
    private String isshow;

    @ApiModelProperty("排序字段")
    private Integer orderby;

    @ApiModelProperty("是否叶子")
    private String isleaf;

}
