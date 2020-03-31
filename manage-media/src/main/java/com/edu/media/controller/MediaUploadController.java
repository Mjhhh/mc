package com.edu.media.controller;

import com.edu.api.media.MediaUploadControllerApi;
import com.edu.framework.domain.media.response.CheckChunkResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.media.service.MediaUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media/upload")
public class MediaUploadController implements MediaUploadControllerApi {

    @Autowired
    MediaUploadService mediaUploadService;

    @Override
    @PostMapping("/register")
    public ResponseResult register(@RequestParam String fileMd5,
                                   @RequestParam String fileName,
                                   @RequestParam Long fileSize,
                                   @RequestParam String mimetype,
                                   @RequestParam String fileExt,
                                   @RequestParam String companyId) {
        return this.mediaUploadService.register(fileMd5, fileName, fileSize, mimetype, fileExt, companyId);
    }

    @Override
    @PostMapping("/checkchunk")
    public CheckChunkResult checkchunk(@RequestParam String fileMd5,
                                       @RequestParam Integer chunk,
                                       @RequestParam Integer chunkSize) {
        return this.mediaUploadService.checkchunk(fileMd5, chunk, chunkSize);
    }

    @Override
    @PostMapping("/uploadchunk")
    public ResponseResult uploadchunk(@RequestParam MultipartFile file,
                                      @RequestParam Integer chunk,
                                      @RequestParam String fileMd5) {
        return this.mediaUploadService.uploadchunk(file, chunk, fileMd5);
    }

    @Override
    @PostMapping("/mergechunks")
    public ResponseResult mergechunks(@RequestParam String fileMd5,
                                      @RequestParam String fileName,
                                      @RequestParam Long fileSize,
                                      @RequestParam String mimetype,
                                      @RequestParam String fileExt,
                                      @RequestParam String companyId) {
        return this.mediaUploadService.mergechunks(fileMd5, fileName, fileSize, mimetype, fileExt, companyId);
    }

}
