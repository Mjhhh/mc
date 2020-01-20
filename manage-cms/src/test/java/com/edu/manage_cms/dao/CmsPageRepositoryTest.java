package com.edu.manage_cms.dao;

import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.CmsPageParam;
import com.edu.manage_cms.service.CmsPageService;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsPageService cmsPageService;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Test
    public void testFindAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    //分页查询测试
    @Test
    public void testFindPage() {
        int page = 0;//从0开始
        int size = 10;//每页记录数
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //保存数据测试
    @Test
    public void testSave() {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("4028e58161bd3b380161bd3bcd2f0000.html");
        cmsPage.setPageAliase("test002");
        cmsPage.setPageWebPath("/coursepre/");
        cmsPage.setPagePhysicalPath("F:\\develop\\xc_portal_static\\course\\detail\\");
        cmsPage.setPageType("1");
        cmsPage.setPageCreateTime(new Date());
        cmsPage.setTemplateId("5a925be7b00ffc4b3c1578b5");
        cmsPage.setHtmlFileId("5ae1973b0e6618644cd7a6fb");
        cmsPage.setDataUrl("http://localhost:40200/portalview/course/get/4028e581617f945f01617f9dabc40000");
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setTemplateId("t01");

        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();

        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);

        cmsPageRepository.save(cmsPage);

        System.out.println(cmsPage);
    }

    @Test
    public void testDelete() {
        cmsPageRepository.deleteById("5aed94530e66185b64804c11");
    }

    @Test
    public void testUpdate() {
        Optional<CmsPage> one = cmsPageRepository.findById("5a754adf6abb500ad05688d9");
        if (one.isPresent()) {
            CmsPage cmsPage = one.get();
            cmsPage.setPageName("更新页");
            cmsPageRepository.save(cmsPage);
        }
    }

    //自定义dao
    @Test
    public void testFindByPageName() {
        CmsPage byPageName = cmsPageRepository.findByPageName("10101.html");
        System.out.println(byPageName);
    }

    //自定义条件查询测试
    @Test
    public void testFindSelfDefind() {
        //分页
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        //模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        //设置条件
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageAliase("详情");
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
//        List<CmsPage> all = cmsPageRepository.findAll(example);
//        List<CmsPage> content = all.getContent();
        System.out.println(all);
    }

    @Test
    public void testFindSelfDefindTwo() {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setPageWebPath("/course/");
        cmsPage.setPageName("4028858162e0bc0a0162e0bfdf1a0000.html");

        Example<CmsPage> example = Example.of(cmsPage);

        List<CmsPage> all = cmsPageRepository.findAll(example);
        System.out.println(all);
    }

    @Test
    public void testGetHtml() {
        String pageHtml = cmsPageService.getPageHtml("5dfa454e59530926a4a49585");
        System.out.println(pageHtml);
    }

    @Test
    public void testGridFs() throws FileNotFoundException {
        //要存储的文件
        File file = new File("D:/index_banner.html");
        //定义输入流
        FileInputStream inputStram = new FileInputStream(file);
        //向GridFS存储文件
        ObjectId objectId = gridFsTemplate.store(inputStram, "轮播图测试文件01.html", "");
        //得到文件ID
        System.out.println(objectId.toString());
    }
}