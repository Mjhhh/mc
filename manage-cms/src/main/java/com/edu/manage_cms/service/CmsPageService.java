package com.edu.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.CmsTemplate;
import com.edu.framework.domain.cms.request.QueryPageRequest;
import com.edu.framework.domain.cms.response.CmsCode;
import com.edu.framework.domain.cms.response.CmsPageResult;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.model.response.QueryResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_cms.config.RabbitmqConfig;
import com.edu.manage_cms.dao.CmsPageRepository;
import com.edu.manage_cms.dao.CmsTemplateRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author mjh
 */
@Service
public class CmsPageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Resource
    GridFSBucket gridFSBucket;

    /**
     * 页面列表分页查询
     *
     * @param page             当前页码
     * @param size             页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        //条件匹配器
        //页面名称模糊查询，需要自定义字符串的匹配器实现模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());

        //条件值
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(StringUtils.isNotEmpty(queryPageRequest.getSiteId()) ? queryPageRequest.getSiteId() : null);
        cmsPage.setPageAliase(StringUtils.isNotEmpty(queryPageRequest.getPageAliase()) ? queryPageRequest.getPageAliase() : null);
        cmsPage.setPageName(StringUtils.isNotEmpty(queryPageRequest.getPageName()) ? queryPageRequest.getPageName() : null);
        cmsPage.setPageType(StringUtils.isNotEmpty(queryPageRequest.getPageType()) ? queryPageRequest.getPageType() : null);

        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }

        page = page - 1;
        //分页对象
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<CmsPage> all = cmsPageRepository.findAll(example, pageRequest);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();
        cmsPageQueryResult.setList(all.getContent());
        cmsPageQueryResult.setTotal(all.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }

    /**
     * 添加页面
     *
     * @param cmsPage 页面的信息
     * @return 新增的页面
     */
    public CmsPageResult add(CmsPage cmsPage) {
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_PARAMS_ISNULL);
        }
        //校验页面是否存在，根据页面名称、站点Id、页面webpath查询
        CmsPage selectCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (selectCmsPage != null) {
            //检验页面是否存在，已经存在则抛出异常
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        //主键由spring data自动生成
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        //返回结果
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    /**
     * 根据id获取单个页面信息
     *
     * @param id
     * @return 页面信息
     */
    public CmsPage getById(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_PARAMS_ISNULL);
        }
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            ExceptionCast.cast(CmsCode.CMS_PAGE_IS_NOT_EXISTS);
        }
        return null;
    }

    /**
     * 更新页面信息
     *
     * @param id      页面id
     * @param cmsPage 更新之后的页面
     * @return 返回操作信息
     */
    public CmsPageResult update(String id, CmsPage cmsPage) {
        if (StringUtils.isBlank(id) || cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_PARAMS_ISNULL);
        }
        CmsPage one = this.getById(id);
        one.setTemplateId(cmsPage.getTemplateId());
        //更新所属站点
        one.setSiteId(cmsPage.getSiteId());
        //更新页面别名
        one.setPageAliase(cmsPage.getPageAliase());
        //更新页面名称
        one.setPageName(cmsPage.getPageName());
        //更新访问路径
        one.setPageWebPath(cmsPage.getPageWebPath());
        //更新物理路径
        one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
        //更新dataUrl
        one.setDataUrl(cmsPage.getDataUrl());
        //执行更新
        CmsPage save = cmsPageRepository.save(one);
        if (save == null) {
            ExceptionCast.cast(CmsCode.CMS_SAVE_PAGE_FAIL);
        }
        return new CmsPageResult(CommonCode.SUCCESS, save);
    }

    /**
     * 删除页面
     *
     * @param id 页面id
     * @return 返回操作信息
     */
    public ResponseResult delete(String id) {
        Optional<CmsPage> cmsPage = cmsPageRepository.findById(id);
        if (cmsPage.isPresent()) {
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        } else {
            ExceptionCast.cast(CmsCode.CMS_PAGE_IS_NOT_EXISTS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * 页面静态化
     *
     * @param pageId 页面id
     * @return 静态化页面字符串
     */
    public String getPageHtml(String pageId) {
        //获取页面模型数据
        Map model = this.getModelByPageId(pageId);
        if (model == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //获取页面模板
        String temContent = this.getTemplateByPageId(pageId);
        if (StringUtils.isEmpty(temContent)) {
            //页面模板为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //执行静态化
        String html = this.generateHtml(temContent, model);
        if (StringUtils.isEmpty(html)) {
            //静态化页面为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;
    }

    /**
     * 页面静态化
     * @param template 模板页面
     * @param model 模型数据
     * @return 静态化页面
     */
    private String generateHtml(String template, Map model) {
        try {
            //生成配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", template);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            //获取模板
            Template templateData = configuration.getTemplate("template");
            return FreeMarkerTemplateUtils.processTemplateIntoString(templateData, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取页面模板
     *
     * @param pageId 页面id
     * @return 页面模板
     */
    private String getTemplateByPageId(String pageId) {
        //查询页面信息
        CmsPage cmsPage = this.getById(pageId);
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if (optional.isPresent()) {
            CmsTemplate cmsTemplate = optional.get();
            //模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //模板文件内容
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象
//            assert gridFSFile != null;
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建GridFsResources
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            try {
                return IOUtils.toString(gridFsResource.getInputStream(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取页面模型数据
     *
     * @param pageId 页面id
     * @return 模板数据
     */
    private Map getModelByPageId(String pageId) {
        //查询页面信息
        CmsPage cmsPage = this.getById(pageId);
        //取出dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        return forEntity.getBody();
    }

    /**
     * 页面发布
     * @param pageId 页面id
     * @return 返回结果
     */
    public ResponseResult postPage(String pageId) {
        //执行静态化
        String pageHtml = this.getPageHtml(pageId);
        if (StringUtils.isEmpty(pageHtml)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        //保存静态化文件
        CmsPage cmsPage = this.saveHtml(pageId, pageHtml);
        //发送消息
        this.sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 保存静态页面内容
     * @param pageId 页面id
     * @param content 页面内容
     * @return cms页面对象
     */
    private CmsPage saveHtml(String pageId, String content) {
        //查询页面
        CmsPage cmsPage = this.getById(pageId);
        String htmlFileId = cmsPage.getHtmlFileId();
        if (StringUtils.isNotEmpty(htmlFileId)) {
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));
        }
        //保存html文件到GridFS
        InputStream inputStream = IOUtils.toInputStream(content, StandardCharsets.UTF_8);
        ObjectId objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        //文件id
        String fileId = objectId.toString();
        //将文件id存储到cmsPage中
        cmsPage.setHtmlFileId(fileId);
        cmsPageRepository.save(cmsPage);
        return cmsPage;
    }

    /**
     * 发送页面发布消息给mq
     * @param pageId 页面id
     */
    private void sendPostPage(String pageId) {
        CmsPage cmsPage = this.getById(pageId);
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("pageId", pageId);
        //消息内容
        String msg = JSON.toJSONString(msgMap);
        //获取站点id作为routingKey
        String siteId = cmsPage.getSiteId();
        //发送消息
        this.rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, siteId, msg);
    }
}
