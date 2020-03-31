package com.edu.framework.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class CmsCourseConfig {
    private String id;
    private String pic;
    private String name;
    private String grade;
    private Float price;
    private Float priceOld;
    private int learningNum;
    private String description;
}
