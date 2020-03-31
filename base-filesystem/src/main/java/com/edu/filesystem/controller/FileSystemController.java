package com.edu.filesystem.controller;

import com.edu.api.filesystem.FileSystemControllerApi;
import com.edu.filesystem.service.FileSystemService;
import com.edu.framework.domain.filesystem.response.UploadFileResult;
import com.edu.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/filesystem")
public class FileSystemController implements FileSystemControllerApi {

    @Autowired
    FileSystemService fileSystemService;

    @Override
    @PostMapping("/upload")
    public UploadFileResult upload(@RequestParam("file") MultipartFile file,
                                   @RequestParam String filetag,
                                   @RequestParam(required = false) String businesskey,
                                   @RequestParam(required = false) String metadata) {
        return fileSystemService.upload(file, filetag, businesskey, metadata);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestParam String fileId) {
        return fileSystemService.delete(fileId);
    }


}

