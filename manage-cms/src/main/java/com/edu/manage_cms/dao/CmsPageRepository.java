package com.edu.manage_cms.dao;


import com.edu.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author mjh
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

    /**
     * 根据页面名查询
     * @param pageName 页面名称
     * @return
     */
    CmsPage findByPageName(String pageName);

    /**
     * 根据页面名和类型查询
     * @param pageName 页面名称
     * @param pageType 页面类型
     * @return
     */
    CmsPage findByPageNameAndPageType(String pageName, String pageType);

    /**
     * 根据页面名称、站点ID、页面访问路径查询
     * @param pageName 页面名称
     * @param siteId 站点id
     * @param pageWebPath 页面访问路径查询
     * @return
     */
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);

    /**
     * 根据模板id查询页面
     * @param templateId
     * @return
     */
    CmsPage findByTemplateId(String templateId);

    /**
     * 根据模板id删除页面
     * @param templateId
     */
    void deleteByTemplateId(String templateId);
}
