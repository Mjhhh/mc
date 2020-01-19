package com.edu.manage_cms_client.service;

import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.CmsSite;
import com.edu.framework.domain.cms.response.CmsCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.manage_cms_client.dao.CmsPageRepository;
import com.edu.manage_cms_client.dao.CmsSiteRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.Optional;

@Service
public class CmsPageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsPageService.class);

    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Resource
    GridFSBucket gridFSBucket;

    public void savePageToServerPath(String pageId) {
        CmsPage cmsPage = findCmsPageById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_IS_NOT_EXISTS);
        }
        //页面所属站点
        CmsSite cmsSite = this.getCmsSiteById(cmsPage.getSiteId());
        //页面物理路径
        String pagePath = cmsSite.getSitePhysicalPath() + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
        //查询页面静态文件
        String htmlFileId = cmsPage.getHtmlFileId();
        InputStream inputStream = this.getFileById(htmlFileId);
        if (inputStream == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        try(FileOutputStream fileOutputStream = new FileOutputStream(new File(pagePath))) {
            IOUtils.copy(inputStream, fileOutputStream);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据页面id查询页面信息
     * @param pageId 页面id
     * @return 页面信息
     */
    public CmsPage findCmsPageById(String pageId) {
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        return optional.orElse(null);
    }

    /**
     * 根据文件id获取文件内容
     * @param fileId 文件id
     * @return 文件的输入流
     */
    public InputStream getFileById(String fileId) {
        try {
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取站点信息
     * @param siteId 站点id
     * @return
     */
    public CmsSite getCmsSiteById(String siteId) {
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        return optional.orElse(null);
    }
}
