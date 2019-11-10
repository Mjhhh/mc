package com.edu.manage_cms.dao;


import com.edu.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

    //根据页面名查询
    public CmsPage findByPageName(String pageName);
    //根据页面名和类型查询
    public CmsPage findByPageNameAndPageType(String pageName, String pageType);
}
