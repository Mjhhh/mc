package com.edu.framework.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mp4VideoUtil extends VideoUtil {

    /**
     * ffmpeg的安装位置
     */
    String ffmpeg_path = "D:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe";
    String video_path = "D:\\Sources\\video\\";
    String mp4_name = "lucene.avi";
    String mp4folder_path = "D:/Sources/video/ffmpeg_test/";
    public Mp4VideoUtil(String ffmpeg_path, String video_path, String mp4_name, String mp4folder_path){
        super(ffmpeg_path);
        this.ffmpeg_path = ffmpeg_path;
        this.video_path = video_path;
        this.mp4_name = mp4_name;
        this.mp4folder_path = mp4folder_path;
    }

    /**
     * 清除已生成的mp4
     * @param mp4Path
     */
    private void clearMp4(String mp4Path){
        //删除原来已经生成的m3u8及ts文件
        File mp4File = new File(mp4Path);
        if(mp4File.exists() && mp4File.isFile()){
            mp4File.delete();
        }
    }
    /**
     * 视频编码，生成mp4文件
     * @return 成功返回success，失败返回控制台日志
     */
    public String generateMp4(){
        //清除已生成的mp4
        clearMp4(mp4folder_path+mp4_name);
        /*
        ffmpeg.exe -i lucene.avi -c:v libx264 -s 1280x720 -pix_fmt yuv420p -b:a 63k -b:v 753k -r 18 .\lucene.mp4
         */
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpeg_path);
        commend.add("-i");
        commend.add(video_path);
        commend.add("-c:v");
        commend.add("libx264");
        //覆盖输出文件
        commend.add("-y");
        commend.add("-s");
        commend.add("1280x720");
        commend.add("-pix_fmt");
        commend.add("yuv420p");
        commend.add("-b:a");
        commend.add("63k");
        commend.add("-b:v");
        commend.add("753k");
        commend.add("-r");
        commend.add("18");
        commend.add(mp4folder_path  + mp4_name );
        String outstring = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            //将标准输入流和错误输入流合并，通过标准输入流程读取信息
            builder.redirectErrorStream(true);
            Process p = builder.start();
            outstring = waitFor(p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Boolean checkVideoTime = this.check_video_time(video_path, mp4folder_path + mp4_name);
        if(!checkVideoTime){
            return outstring;
        }else{
            return "success";
        }
    }

    public static void main(String[] args) throws IOException {
        //ffmpeg的安装位置
        String ffmpeg_path = "D:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe";
        String video_path = "D:\\Sources\\video\\lucene.avi";
        String mp4_name = "lucene.mp4";
        String mp4_path = "D:/Sources/video/ffmpeg_test/";
        Mp4VideoUtil videoUtil = new Mp4VideoUtil(ffmpeg_path,video_path,mp4_name,mp4_path);
        String s = videoUtil.generateMp4();
        System.out.println(s);
    }
}
