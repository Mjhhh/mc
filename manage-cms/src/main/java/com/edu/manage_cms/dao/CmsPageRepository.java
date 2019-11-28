package com.edu.manage_cms.dao;


import com.edu.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

    //根据页面名查询
    CmsPage findByPageName(String pageName);
    //根据页面名和类型查询
    CmsPage findByPageNameAndPageType(String pageName, String pageType);
    //根据页面名称、站点ID、页面访问路径查询
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}
