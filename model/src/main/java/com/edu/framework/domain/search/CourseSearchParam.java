package com.edu.framework.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class CourseSearchParam implements Serializable {
    @ApiModelProperty("关键字")
    private String keyword;
    @ApiModelProperty("一级分类")
    private String mt;
    @ApiModelProperty("二级分类")
    private String st;
    @ApiModelProperty("难度等级")
    private String grade;
    @ApiModelProperty("价格区间")
    private Float priceMin;
    private Float priceMax;
    @ApiModelProperty("排序字段")
    private String sort;
    @ApiModelProperty("过虑字段")
    private String filter;
}
