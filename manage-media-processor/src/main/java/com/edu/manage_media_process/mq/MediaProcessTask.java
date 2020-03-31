package com.edu.manage_media_process.mq;

import com.alibaba.fastjson.JSON;
import com.edu.framework.domain.media.MediaFile;
import com.edu.framework.domain.media.MediaFileProcess_m3u8;
import com.edu.framework.utils.HlsVideoUtil;
import com.edu.framework.utils.Mp4VideoUtil;
import com.edu.manage_media_process.dao.MediaFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class MediaProcessTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaProcessTask.class);

    /**
     * ffmpeg绝对路径
     */
    @Value("${manage-media.ffmpeg-path}")
    String ffmpegPath;

    /**
     * 上传文件根目录
     */
    @Value("${manage-media.video-location}")
    String serverPath;


    @Autowired
    MediaFileRepository mediaFileRepository;

    @RabbitListener(queues = "${manage-media.mq.queue-media-video-processor}", containerFactory = "customContainerFactory")
    public void receiverMediaProcessTask(String msg) {
        Map msgMap = JSON.parseObject(msg, Map.class);
        LOGGER.info("receiver media process task msg : {}", msgMap);
        //解析消息
        //媒资文件id
        String mediaId = (String) msgMap.get("mediaId");
        //获取媒资文件信息
        Optional<MediaFile> optional = mediaFileRepository.findById(mediaId);
        if (!optional.isPresent()) {
            return;
        }
        MediaFile mediaFile = optional.get();
        //媒资文件类型
        String fileType = mediaFile.getFileType();
        if (fileType == null || (!StringUtils.equals(fileType, "avi") && !StringUtils.equals(fileType, "mp4"))) {
            //处理状态为无需处理
            mediaFile.setProcessStatus("303004");
            mediaFileRepository.save(mediaFile);
            return;
        } else {
            //处理状态为未处理
            mediaFile.setProcessStatus("303001");
            mediaFileRepository.save(mediaFile);
        }
        String videoPath = serverPath + mediaFile.getFilePath() + mediaFile.getFileName();
        String mp4Name = mediaFile.getFileId() + ".mp4";
        String result;
        if (StringUtils.equals(fileType, "avi")) {
            //生成mp4
            String mp4FolderPath = serverPath + mediaFile.getFilePath();
            Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpegPath, videoPath, mp4Name, mp4FolderPath);
            result = mp4VideoUtil.generateMp4();
            if (result == null || !StringUtils.equals(result, "success")) {
                //操作失败写入处理日志
                //处理状态为处理失败
                mediaFile.setProcessStatus("303003");
                MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
                mediaFileProcess_m3u8.setErrormsg(result);
                mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
                mediaFileRepository.save(mediaFile);
                return;
            }
        }
        String fileMd5 = mediaFile.getFileId();
        //删除分块文件
        String fileFolderPath = serverPath + mediaFile.getFilePath() + "chunks";
        File chunksPath = new File(fileFolderPath);
        if (chunksPath.exists()) {
            File[] files = chunksPath.listFiles();
            if (files.length > 0) {
                for (File file : files) {
                    file.delete();
                }
            }
            chunksPath.delete();
        }
        //生成m3u8
        //此地址为mp4的地址
        videoPath = serverPath + mediaFile.getFilePath() + mp4Name;
        String m3u8Name = mediaFile.getFileId() + ".m3u8";
        String m3u8folderPath = serverPath + mediaFile.getFilePath() + "hls/";
        HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(ffmpegPath, videoPath, m3u8Name, m3u8folderPath);
        result = hlsVideoUtil.generateM3u8();
        if (result == null || !StringUtils.equals(result,"success")) {
            //操作失败写入处理日志
            //处理状态为处理失败
            mediaFile.setProcessStatus("303003");
            MediaFileProcess_m3u8 mediaFileProcessM3u8 = new MediaFileProcess_m3u8();
            mediaFileProcessM3u8.setErrormsg(result);
            mediaFile.setMediaFileProcess_m3u8(mediaFileProcessM3u8);
            mediaFileRepository.save(mediaFile);
            return;
        }
        //获取m3u8列表
        List<String> tsList = hlsVideoUtil.get_ts_list();
        //更新处理状态为成功
        //处理状态为处理成功
        mediaFile.setProcessStatus("303002");
        MediaFileProcess_m3u8 mediaFileProcessM3u8 = new MediaFileProcess_m3u8();
        mediaFileProcessM3u8.setTslist(tsList);
        mediaFile.setMediaFileProcess_m3u8(mediaFileProcessM3u8);
        //m3u8文件url
        mediaFile.setFileUrl(mediaFile.getFilePath() + "hls/" + m3u8Name);
        mediaFileRepository.save(mediaFile);
    }
}
