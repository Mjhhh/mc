package com.edu.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.edu.filesystem.dao.FileSystemRepository;
import com.edu.framework.domain.filesystem.FileSystem;
import com.edu.framework.domain.filesystem.response.FileSystemCode;
import com.edu.framework.domain.filesystem.response.UploadFileResult;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class FileSystemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemService.class);

    @Value("${edu.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${edu.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${edu.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${edu.fastdfs.charset}")
    String charset;

    @Autowired
    FileSystemRepository fileSystemRepository;

    /**
     * 获取文件记录
     * @return
     */
    private FileSystem getFileSystem(String fileId) {
        if (StringUtils.isBlank(fileId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Optional<FileSystem> optional = fileSystemRepository.findById(fileId);
        return optional.orElse(null);
    }

    /**
     * 加载fdfs的配置
     */
    private void initFdfsConfig(){
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
        } catch (Exception e) {
            e.printStackTrace();
            //初始化文件系统出错
            ExceptionCast.cast(FileSystemCode.FS_INITFDFSERROR);
        }
    }

    /**
     * 上传文件到fdfs
     * @param file 文件
     * @return 文件id
     */
    private String fdfs_upload(MultipartFile file) {
        try {
            //加载fdfs的配置
            this.initFdfsConfig();
            //创建tracker client
            TrackerClient trackerClient = new TrackerClient();
            //获取trackerServer
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建storage client
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            //上传文件
            //文件字节
            byte[] bytes = file.getBytes();
            //文件原始名称
            String originalFilename = file.getOriginalFilename();
            //文件扩展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //文件id
            String uploadFile = storageClient1.upload_file1(bytes, extName, null);
            return uploadFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从fdfs删除文件
     * @param fileId
     * @return
     */
    private boolean fdfs_deletefile(String fileId) {
        try {
            //加载fdfs的配置
            this.initFdfsConfig();
            //创建tracker client
            TrackerClient trackerClient = new TrackerClient();
            //获取trackerServer
            TrackerServer trackerServer = null;
            trackerServer = trackerClient.getConnection();
            //获取storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建storage client
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            int result = storageClient1.delete_file1(fileId);
            if (result == 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 上传文件
     * @param file 文件
     * @param filetag 文件标识
     * @param businesskey 业务标识
     * @param metadata 文件元数据
     * @return 提示信息
     */
    @Transactional
    public UploadFileResult upload(MultipartFile file, String filetag, String businesskey, String metadata) {
        if (file == null) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        //上传文件到fdfs
        String fileId = this.fdfs_upload(file);
        //创建文件信息对象
        FileSystem fileSystem = new FileSystem();
        //文件id
        fileSystem.setFileId(fileId);
        //文件路径
        fileSystem.setFilePath(fileId);
        //业务标识
        fileSystem.setBusinesskey(businesskey);
        //标签
        fileSystem.setFiletag(filetag);
        //元数据
        if (StringUtils.isNotBlank(metadata)) {
            Map map = JSON.parseObject(metadata, Map.class);
            fileSystem.setMetadata(map);
        }
        //名称
        fileSystem.setFileName(file.getOriginalFilename());
        //大小
        fileSystem.setFileSize(file.getSize());
        //文件类型
        fileSystem.setFileType(file.getContentType());
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS, fileSystem);
    }

    /**
     * 删除文件
     * @param fileId
     * @return
     */
    @Transactional
    public ResponseResult delete(String fileId) {
        FileSystem fileSystem = this.getFileSystem(fileId);
        if (fileSystem == null) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        this.fdfs_deletefile(fileId);
        fileSystemRepository.delete(fileSystem);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
