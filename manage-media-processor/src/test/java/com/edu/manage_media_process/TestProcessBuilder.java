package com.edu.manage_media_process;

import com.edu.framework.utils.Mp4VideoUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestProcessBuilder {

    @Test
    public void testProcessBuilder() {
        ProcessBuilder processBuilder = new ProcessBuilder();
//        processBuilder.command("ping", "127.0.0.1");
        processBuilder.command("ipconfig");
        processBuilder.redirectErrorStream(true);
        try {
            //启动进程
            Process start = processBuilder.start();
            //获取输入流
            InputStream inputStream = start.getInputStream();
            //转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");

            int len = -1;
            char[] c = new char[1024];
            StringBuffer outputString = new StringBuffer();
            //读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String temp = new String(c, 0, len);
                outputString.append(temp);
                System.out.println(temp);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFFmpeg() {
        //ffmpeg_path, video_path, mp4_name, mp4folder_path
        //ffmpeg的路径
        String ffmpeg_path = "D:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe";
        //video_path视频地址
        String video_path = "D:\\Sources\\video\\lucene.avi";
        //mp4_name mp4文件名称
        String mp4_name  ="lucene.mp4";
        //mp4folder_path mp4文件目录路径
        String mp4folder_path="D:/Sources/video/ffmpeg_test/";
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpeg_path,video_path,mp4_name,mp4folder_path);
        //开始编码,如果成功返回success，否则返回输出的日志
        String result = mp4VideoUtil.generateMp4();
        System.out.println(result);
    }

}
