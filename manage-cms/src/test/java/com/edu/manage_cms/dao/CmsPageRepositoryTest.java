package com.edu.manage_cms.dao;

import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.CmsPageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

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
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //保存数据测试
    @Test
    public void testSave() {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        cmsPage.setSiteId("s01");
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
}
