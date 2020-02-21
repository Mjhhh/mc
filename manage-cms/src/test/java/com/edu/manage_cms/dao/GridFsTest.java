package com.edu.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * 存文件
     * @throws FileNotFoundException
     */
    @Test
    public void testStore() throws FileNotFoundException {
        String classpath = this.getClass().getResource("/").getPath() + "/templates/";
        //定义file
        File file =new File(classpath + "course.ftl");
        //定义fileInputStream
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectId objectId = gridFsTemplate.store(fileInputStream, "课程详情模板文件");
        System.out.println(objectId);
    }
}
