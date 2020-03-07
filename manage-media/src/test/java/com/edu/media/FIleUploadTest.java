package com.edu.media;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FIleUploadTest {
    @Test
    public void testChunk() throws IOException {
        //目标文件
        File sourceFile = new File("D:/Sources/video/lucene.avi");
        //块文件夹
        File chunkFolder = new File("D:/Sources/video/chunk/");
        if (!chunkFolder.exists()) {
            chunkFolder.mkdirs();
        }
        //分块大小
        long chunkSize = (1024 * 1024);
        //分块数量
        long chunckNum = (long) Math.ceil(sourceFile.length() * 1.0 / chunkSize);
        if (chunckNum <= 0) {
            chunckNum = 1;
        }
        //缓冲区大小
        byte[] bytes = new byte[1024];
        //使用RandomAccessFile访问文件
        RandomAccessFile rafRead = new RandomAccessFile(sourceFile, "r");
        //分块
        for (int i = 0; i < chunckNum; i++) {
            //创建分块文件
            File file = new File("D:/Sources/video/chunk/" + i);
            boolean newFile = file.createNewFile();
            if (newFile) {
                //向分块文件中写数据
                RandomAccessFile rafWrite = new RandomAccessFile(file, "rw");
                int len = -1;
                while ((len = rafRead.read(bytes)) != -1) {
                    rafWrite.write(bytes, 0, len);
                    if (file.length() > chunkSize) {
                        break;
                    }
                }
                rafWrite.close();
            }
        }
        rafRead.close();
    }

    @Test
    public void testMmerge() throws IOException {
        //块文件目录
        File chunkFolder = new File("D:/Sources/video/chunk/");
        //合并文件
        File mergeFile = new File("D:/Sources/video/luceneMerge.avi");
        if (mergeFile.exists()) {
            mergeFile.delete();
        }
        //创建新的合并文件
        mergeFile.createNewFile();
        //用于写文件
        RandomAccessFile rafWrite = new RandomAccessFile(mergeFile, "rw");
        //指针指向文件顶端
        rafWrite.seek(0);
        //缓冲区
        byte[] bytes = new byte[1024];
        //分块列表
        File[] files = chunkFolder.listFiles();
        //转成集合，便于排序
        List<File> fileList = new ArrayList<>(Arrays.asList(files));
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                    return -1;
                }
                return 1;
            }
        });
        //合并文件
        for (File chunkFile : fileList) {
            RandomAccessFile rafRead = new RandomAccessFile(chunkFile, "rw");
            int len = -1;
            while ((len = rafRead.read(bytes)) != -1) {
                rafWrite.write(bytes, 0, len);
            }
            rafRead.close();
        }
        rafWrite.close();
    }

}
