package com.edu.framework.domain.filesystem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@ToString
@Document(collection = "filesystem")
public class FileSystem {

    @Id
    private String fileId;
    @ApiModelProperty("文件请求路径")
    private String filePath;
    @ApiModelProperty("文件大小")
    private long fileSize;
    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("文件类型")
    private String fileType;
    @ApiModelProperty("图片宽度")
    private int fileWidth;
    @ApiModelProperty("图片高度")
    private int fileHeight;
    @ApiModelProperty("用户id，用于授权")
    private String userId;
    @ApiModelProperty("业务key")
    private String businesskey;
    @ApiModelProperty("业务标签")
    private String filetag;
    @ApiModelProperty("文件元信息")
    private Map metadata;

}
