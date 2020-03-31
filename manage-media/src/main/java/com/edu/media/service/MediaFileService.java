package com.edu.media.service;

import com.edu.framework.domain.media.MediaFile;
import com.edu.framework.domain.media.request.QueryMediaFileRequest;
import com.edu.framework.domain.media.response.MediaCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.utils.McOauth2Util;
import com.edu.media.client.CourseClient;
import com.edu.media.dao.MediaFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MediaFileService {

    private static Logger logger = LoggerFactory.getLogger(MediaFileService.class);

    /**
     * 上传文件根目录
     */
    @Value("${manage-media.upload-location}")
    String uploadPath;

    @Autowired
    MediaFileRepository mediaFileRepository;

    @Autowired
    CourseClient courseClient;

    /**
     * 删除文件和文件夹
     */
    private void deleteFile(File deleteFile) {
        if (deleteFile.isDirectory()) {
            File[] files = deleteFile.listFiles();
            if (files.length > 0) {
                for (File file : files) {
                    this.deleteFile(file);
                }
            }
            deleteFile.delete();
        } else {
            deleteFile.delete();
        }
    }

    /**
     * 删除视频文件
     *
     * @param id
     * @return
     */
    private MediaFile getMediaFile(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Optional<MediaFile> optional = mediaFileRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 查询文件列表
     *
     * @param page             页码
     * @param size             显示数
     * @param mediaFileRequest 请求的参数
     * @return
     */
    public CommonResponseResult findList(int page, int size, QueryMediaFileRequest mediaFileRequest) {
        HttpServletRequest request = this.getRequest();
        //调用工具类取出用户信息
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (userJwt == null) {
            ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        }
        if (StringUtils.isBlank(userJwt.getCompanyId())) {
            ExceptionCast.cast(CommonCode.MISS_COMPANY_ID);
        }
        MediaFile mediaFile = new MediaFile();
        String companyId = userJwt.getCompanyId();
        mediaFile.setCompanyId(companyId);
        //设置查询条件
        //查询条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains())
                //处理状态精确匹配（默认）
                .withMatcher("processStatus", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("companyId", ExampleMatcher.GenericPropertyMatchers.exact());
        if (mediaFileRequest == null) {
            mediaFileRequest = new QueryMediaFileRequest();
        }
        //查询条件对象
        if (StringUtils.isNotBlank(mediaFileRequest.getFileOriginalName())) {
            mediaFile.setFileOriginalName(mediaFileRequest.getFileOriginalName());
        }
        if (StringUtils.isNotBlank(mediaFileRequest.getProcessStatus())) {
            mediaFile.setProcessStatus(mediaFileRequest.getProcessStatus());
        }
        if (StringUtils.isNotBlank(mediaFileRequest.getTag())) {
            mediaFile.setTag(mediaFileRequest.getTag());
        }
        //定义example示例
        Example<MediaFile> example = Example.of(mediaFile, exampleMatcher);

        //分页
        page = page - 1;
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        //创建分页对象
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MediaFile> all = mediaFileRepository.findAll(example, pageRequest);
        Map<String, Object> result = new HashMap<>();
        result.put("total", all.getTotalElements());
        result.put("list", all.getContent());

        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }

    /**
     * 删除视频文件
     *
     * @param fileId
     * @return
     */
    @Transactional
    public ResponseResult deleteVideo(String fileId) {
        MediaFile mediaFile = this.getMediaFile(fileId);
        if (mediaFile == null) {
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_ISNULL);
        }
        //删除视频课程映射表
        courseClient.delTeachplanMedia(fileId);
        String filePath = uploadPath + mediaFile.getFilePath();
        File file = new File(filePath);
        this.deleteFile(file);
        mediaFileRepository.delete(mediaFile);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
