package com.edu.media.controller;

import com.edu.api.media.MediaFileControllerApi;
import com.edu.framework.domain.media.request.QueryMediaFileRequest;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.media.service.MediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/media/file")
public class MediaFileController implements MediaFileControllerApi {

    @Autowired
    MediaFileService mediaFileService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public CommonResponseResult findList(@PathVariable int page,@PathVariable int size, QueryMediaFileRequest mediaFileRequest) {
        return this.mediaFileService.findList(page, size, mediaFileRequest);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteVideo(@PathVariable("id") String fileId) {
        return this.mediaFileService.deleteVideo(fileId);
    }
}
