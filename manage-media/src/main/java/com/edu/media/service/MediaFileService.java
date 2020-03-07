package com.edu.media.service;

import com.edu.framework.domain.media.MediaFile;
import com.edu.framework.domain.media.request.QueryMediaFileRequest;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.media.dao.MediaFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MediaFileService {

    private static Logger logger = LoggerFactory.getLogger(MediaFileService.class);

    @Autowired
    MediaFileRepository mediaFileRepository;

    /**
     * 查询文件列表
     * @param page 页码
     * @param size 显示数
     * @param mediaFileRequest 请求的参数
     * @return
     */
    public CommonResponseResult findList(int page, int size, QueryMediaFileRequest mediaFileRequest) {
        //设置查询条件
        MediaFile mediaFile = new MediaFile();
        //查询条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("processStatus", ExampleMatcher.GenericPropertyMatchers.exact());//处理状态精确匹配（默认）
        if (mediaFileRequest == null) {
            mediaFileRequest = new QueryMediaFileRequest();
        }
        //查询条件对象
        if (StringUtils.isNotBlank(mediaFileRequest.getFileOriginalName())){
            mediaFile.setFileOriginalName(mediaFileRequest.getFileOriginalName());
        }
        if (StringUtils.isNotBlank(mediaFileRequest.getProcessStatus())){
            mediaFile.setProcessStatus(mediaFileRequest.getProcessStatus());
        }
        if (StringUtils.isNotBlank(mediaFileRequest.getTag())){
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
}
