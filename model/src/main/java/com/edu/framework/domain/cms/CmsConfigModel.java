package com.edu.framework.domain.cms;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class CmsConfigModel {
    private String id;
    private String pid;
    private String name;
    private String url;
    private List<CmsConfigModel> children;
}
