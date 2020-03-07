package com.edu.framework.domain.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document(collection = "cms_site_server")
public class CmsSiteServer {
    /**
     * 站点id、服务器IP、端口、访问地址、服务器类型（代理、静态、动态、CDN）、资源发布地址（完整的HTTP接口）、使用类型（测试、生产）
     */
    @ApiModelProperty("站点id")
    private String siteId;
    @ApiModelProperty("服务器ID")
    @Id
    private String serverId;
    @ApiModelProperty("服务器IP")
    private String ip;
    @ApiModelProperty("端口")
    private String port;
    @ApiModelProperty("访问地址")
    private String webPath;
    @ApiModelProperty("服务器名称（代理、静态、动态、CDN）")
    private String serverName;
    @ApiModelProperty("资源发布地址（完整的HTTP接口）")
    private String uploadPath;
    @ApiModelProperty("使用类型（测试、生产）")
    private String useType;
}
