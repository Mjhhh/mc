package com.edu.framework.domain.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@ToString
@Document(collection = "cms_page")
public class CmsPage {

    @ApiModelProperty("站点ID")
    private String siteId;

    @ApiModelProperty("页面ID")
    @Id
    private String pageId;

    @ApiModelProperty("页面名称")
    private String pageName;

    @ApiModelProperty("别名")
    private String pageAliase;

    @ApiModelProperty("访问地址")
    private String pageWebPath;

    @ApiModelProperty("参数")
    private String pageParameter;

    @ApiModelProperty("物理路径")
    private String pagePhysicalPath;

    @ApiModelProperty("类型:静态/动态")
    private String pageType;

    @ApiModelProperty("页面模版")
    private String pageTemplate;

    @ApiModelProperty("页面静态化内容")
    private String pageHtml;

    @ApiModelProperty("状态")
    private String pageStatus;

    @ApiModelProperty("创建时间")
    private Date pageCreateTime;

    @ApiModelProperty("模版id")
    private String templateId;

    @ApiModelProperty("参数列表")
    private List<CmsPageParam> pageParams;

    @ApiModelProperty("静态文件Id")
    private String htmlFileId;

    @ApiModelProperty("数据Url")
    private String dataUrl;

}
