package com.edu.media.service;

import com.alibaba.fastjson.JSON;
import com.edu.framework.domain.media.MediaFile;
import com.edu.framework.domain.media.response.CheckChunkResult;
import com.edu.framework.domain.media.response.MediaCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.utils.McOauth2Util;
import com.edu.media.config.RabbitMQConfig;
import com.edu.media.controller.MediaUploadController;
import com.edu.media.dao.MediaFileRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Service
public class MediaUploadService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MediaUploadController.class);

    @Autowired
    MediaFileRepository mediaFileRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 上传文件根目录
     */
    @Value("${manage-media.upload-location}")
    String uploadPath;

    /**
     * 视频处理路由
     */
    @Value("${manage-media.mq.routingkey-media-video}")
    String ROUTINGKEY_MEDIA_VIDEO;

    /**
     * 获取HttpServletRequest
     * @return
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 根据文件md5得到文件的绝对路径
     * 规则：
     * 一级目录：md5的第一个字符
     * 二级目录：md5的第二个字符
     * 三级目录：md5
     * 文件名：md5+文件扩展名
     *
     * @param fileMd5 文件md5值
     * @param fileExt 文件扩展名
     * @return 文件的绝对路径
     */
    private String getFilePath(String fileMd5, String fileExt) {
        String filePath = this.getFileFolderPath(fileMd5) + fileMd5 + "." + fileExt;
        return filePath;
    }

    /**
     * 获取文件的相对路径
     *
     * @param fileMd5 文件md5值
     * @param fileExt 文件的扩展名
     * @return 文件相对路径
     */
    private String getFileRelativePath(String fileMd5, String fileExt) {
        String filePath = fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + fileMd5 + "." + fileExt;
        return filePath;
    }

    /**
     * 得到文件所在的绝对目录
     *
     * @param fileMd5 文件md5值
     * @return 文件所在的绝对目录
     */
    private String getFileFolderPath(String fileMd5) {
        String fileFolderPath = uploadPath + fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/";
        return fileFolderPath;
    }

    /**
     * 得到文件所在的相对目录
     *
     * @param fileMd5 文件md5值
     * @return 文件所在的相对目录
     */
    private String getFileFolderRelativePath(String fileMd5) {
        String fileFolderPath = fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/";
        return fileFolderPath;
    }

    /**
     * 得到块文件所在目录
     *
     * @param fileMd5 文件md5值
     * @return 块文件所在目录
     */
    private String getChunkFileFolderPath(String fileMd5) {
        String fileChunkFolderPath = getFileFolderPath(fileMd5) + "chunks" + "/";
        return fileChunkFolderPath;
    }

    /**
     * 获取所有已经排序的块文件
     *
     * @param chunkfileFolder 块文件目录
     * @return
     */
    private List<File> getChunkFiles(File chunkfileFolder) {
        //获取路径下的所有块文件
        File[] chunkFiles = chunkfileFolder.listFiles();
        //将文件数组转成list，并排序
        assert chunkFiles != null;
        List<File> chunkFileList = new ArrayList<>(Arrays.asList(chunkFiles));
        //排序
        chunkFileList.sort((o1, o2) -> {
            if (Integer.parseInt(o1.getName()) > Integer.parseInt(o2.getName())) {
                return 1;
            } else if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                return -1;
            }
            return 0;
        });
        return chunkFileList;
    }

    /**
     * 创建文件目录
     *
     * @param fileMd5 文件md5值
     * @return boolean
     */
    private boolean createFileFold(String fileMd5) {
        //创建上传文件目录
        String fileFolderPath = getFileFolderPath(fileMd5);
        File fileFolder = new File(fileFolderPath);
        if (!fileFolder.exists()) {
            //创建文件夹
            boolean mkdirs = fileFolder.mkdirs();
            return mkdirs;
        }
        return true;
    }

    /**
     * 创建块文件目录
     *
     * @param fileMd5 文件md5值
     * @return 块文件目录
     */
    private boolean createChunkFileFolder(String fileMd5) {
        //创建上传文件目录
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        File chunkFileFolder = new File(chunkFileFolderPath);
        if (!chunkFileFolder.exists()) {
            //创建文件夹
            boolean mkdirs = chunkFileFolder.mkdirs();
            return mkdirs;
        }
        return true;
    }

    /**
     * 校验文件的md5值
     *
     * @param mergeFile 校验的文件
     * @param md5       文件md5值
     * @return
     */
    private boolean checkFileMd5(File mergeFile, String md5) {
        if (mergeFile == null || StringUtils.isEmpty(md5)) {
            return false;
        }
        //进行md5校验
        try (FileInputStream mergeFileInputstream = new FileInputStream(mergeFile);) {
            //得到文件的md5
            String mergeFileMd5 = DigestUtils.md5Hex(mergeFileInputstream);
            //比较md5
            if (md5.equalsIgnoreCase(mergeFileMd5)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("checkFileMd5 error,file is:{},md5 is: {}", mergeFile.getAbsoluteFile(), md5);
        }
        return false;
    }

    /**
     * 合并文件
     *
     * @param mergeFile  要合并成的文件
     * @param chunkFiles 所有的块文件
     * @return 合并成的文件
     */
    private File mergeFile(File mergeFile, List<File> chunkFiles) {
        try {
            //创建写文件对象
            RandomAccessFile rafWrite = new RandomAccessFile(mergeFile, "rw");
            //遍历分块文件开始合并
            //读取文件缓冲区
            byte[] b = new byte[1024];
            for (File chunkFile : chunkFiles) {
                RandomAccessFile rafRead = new RandomAccessFile(chunkFile, "r");
                int len = -1;
                //读取分块文件
                while ((len = rafRead.read(b)) != -1) {
                    //向合并文件中写数据
                    rafWrite.write(b, 0, len);
                }
                rafRead.close();
            }
            rafWrite.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("merge file error:{}", e.getMessage());
            return null;
        }
        return mergeFile;
    }

    /**
     * 发送视频处理消息
     * @param mediaId
     * @return
     */
    private ResponseResult sendProcessVideoMsg(String mediaId) {
        Optional<MediaFile> optional = mediaFileRepository.findById(mediaId);
        if (!optional.isPresent()) {
            return new ResponseResult(CommonCode.FAIL);
        }
        //发送视频处理消息
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("mediaId", mediaId);
        //发送的消息
        String msg = JSON.toJSONString(msgMap);
        try {
            this.rabbitTemplate.convertAndSend(RabbitMQConfig.EX_MEDIA_PROCESSTASK, ROUTINGKEY_MEDIA_VIDEO, msg);
            LOGGER.info("send media process task msg:{}", msg);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("send media process task error,msg is:{},error:{}", msg, e.getMessage());
            return new ResponseResult(CommonCode.FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 文件上传注册
     *
     * @param fileMd5  文件md5值
     * @param fileName 文件名
     * @param fileSize 文件大小
     * @param mimetype
     * @param fileExt  文件扩展名
     * @return 注册结果
     */
    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt, String companyId) {
        if (StringUtils.isBlank(companyId)) {
            ExceptionCast.cast(CommonCode.MISS_COMPANY_ID);
        }
        //检查文件是否上传
        //1、得到文件的路径
        String filePath = getFilePath(fileMd5, fileExt);
        File file = new File(filePath);

        //2、查询数据库文件是否存在
        Optional<MediaFile> optional = mediaFileRepository.findById(fileMd5);
        //文件存在直接返回
        if (file.exists() && optional.isPresent()) {
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_EXIST);
        }
        //如果文件不存在时，检查文件所在目录是否存在，如果不存在则创建
        boolean fileFold = createFileFold(fileMd5);
//        if (!fileFold) {
//            //上传文件目录创建失败
//            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_CREATEFOLDER_FAIL);
//        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 检查块文件
     *
     * @param fileMd5   文件md5值
     * @param chunk     块的下标
     * @param chunkSize 块大小
     * @return
     */
    public CheckChunkResult checkchunk(String fileMd5, Integer chunk, Integer chunkSize) {
        //得到块文件所在路径
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        //块文件的文件名称以1，2，3....序号命名，没有扩展名
        File chunkFile = new File(chunkFileFolderPath);
        if (chunkFile.exists()) {
            return new CheckChunkResult(MediaCode.CHUNK_FILE_EXIST_CHECK, true);
        } else {
            return new CheckChunkResult(MediaCode.CHUNK_FILE_NOT_EXIST_CHECK, false);
        }
    }

    /**
     * 上传块文件
     *
     * @param file    块文件
     * @param fileMd5 文件md5值
     * @param chunk   块的下标
     * @return
     */
    public ResponseResult uploadchunk(MultipartFile file, Integer chunk, String fileMd5) {
        if (file == null) {
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_ISNULL);
        }
        //创建块文件目录
        boolean fileFold = createChunkFileFolder(fileMd5);
        //块文件
        File chunkfile = new File(getChunkFileFolderPath(fileMd5) + chunk);
        //上传的块文件
        try (InputStream inputStream = file.getInputStream(); FileOutputStream outputStream = new FileOutputStream(chunkfile)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("upload chunk file fail:{}", e.getMessage());
            ExceptionCast.cast(MediaCode.CHUNK_FILE_UPLOAD_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 合并所有块文件
     *
     * @param fileMd5  文件md5值
     * @param fileName 文件名
     * @param fileSize 文件大小
     * @param mimetype
     * @param fileExt
     * @return
     */
    public ResponseResult mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt, String companyId) {
        //合并块文件
        //获取块文件的路径
        String chunkFileFolderPath = this.getChunkFileFolderPath(fileMd5);
        File chunkfileFolder = new File(chunkFileFolderPath);
        //获取合并文件的路径
        File mergeFile = new File(getFilePath(fileMd5, fileExt));
        //创建合并文件
        //合并文件存在先删除再创建
        if (mergeFile.exists()) {
            mergeFile.delete();
        }
        boolean newFile = false;
        try {
            newFile = mergeFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("mergechunks..create mergeFile fail:{}", e.getMessage());
        }
        if (!newFile) {
            ExceptionCast.cast(MediaCode.MERGE_FILE_CREATEFAIL);
        }
        //获取所有已经排序的块文件
        List<File> chunkFiles = getChunkFiles(chunkfileFolder);
        //合并文件
        mergeFile = this.mergeFile(mergeFile, chunkFiles);
        if (mergeFile == null) {
            ExceptionCast.cast(MediaCode.MERGE_FILE_FAIL);
        }
        //校验文件
        boolean checkResult = this.checkFileMd5(mergeFile, fileMd5);
        if (!checkResult) {
            ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
        }
        //将文件信息保存到数据库
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileId(fileMd5);
        mediaFile.setFileName(fileMd5 + "." + fileExt);
        mediaFile.setFileOriginalName(fileName);
        //文件路径保存相对路径
        mediaFile.setFilePath(this.getFileFolderRelativePath(fileMd5));
        mediaFile.setFileSize(fileSize);
        mediaFile.setUploadTime(new Date());
        mediaFile.setMimeType(mimetype);
        mediaFile.setFileType(fileExt);
        mediaFile.setCompanyId(companyId);
        //状态为上传成功
        mediaFile.setFileStatus("301002");
        mediaFileRepository.save(mediaFile);
        String fileId = mediaFile.getFileId();
        //向MQ发送视频处理消息
        this.sendProcessVideoMsg(fileId);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
