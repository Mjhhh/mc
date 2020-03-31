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
@Document(collection = "cms_config")
public class CmsConfig {
    @Id
    private String id;
    private String name;
    private List<CmsConfigModel> model;
    private List<CmsCourseConfig> courseConfigs;

}
