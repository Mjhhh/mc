package com.edu.framework.domain.media;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author Admin
 */
@Data
@ToString
@Document(collection = "media_file")
public class MediaFile {
    /**
     *
     * 文件id、名称、大小、文件类型、文件状态（未上传、上传完成、上传失败）、上传时间、
     * 视频处理方式、视频处理状态、hls_m3u8,hls_ts_list、课程视频信息（课程id、章节id）
     */
    @Id
    @ApiModelProperty("文件id")
    private String fileId;
    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("文件原始名称")
    private String fileOriginalName;
    @ApiModelProperty("文件路径")
    private String filePath;
    @ApiModelProperty("文件url")
    private String fileUrl;
    @ApiModelProperty("文件类型")
    private String fileType;
    @ApiModelProperty("mimetype")
    private String mimeType;
    @ApiModelProperty("文件大小")
    private Long fileSize;
    @ApiModelProperty("文件状态")
    private String fileStatus;
    @ApiModelProperty("上传时间")
    private Date uploadTime;
    @ApiModelProperty("处理状态")
    private String processStatus;
    @ApiModelProperty("hls处理")
    private MediaFileProcess_m3u8 mediaFileProcess_m3u8;
    @ApiModelProperty("tag标签用于查询")
    private String tag;
    @ApiModelProperty("组织id")
    private String companyId;
}
