package com.edu.manage_cms.service;

import com.alibaba.fastjson.JSONObject;
import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.CmsTemplate;
import com.edu.framework.domain.cms.response.CmsCode;
import com.edu.framework.domain.filesystem.FileSystem;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_cms.dao.CmsPageRepository;
import com.edu.manage_cms.dao.CmsTemplateRepository;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class CmsTemplateService {

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    /**
     * 获取页面模板
     *
     * @return
     */
    private CmsTemplate getTemplate(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_PARAMS_ISNULL);
        }
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 查找模板列表
     *
     * @param page
     * @param size
     * @return
     */
    public CommonResponseResult findList(int page, int size, String templateName) {
        page = page - 1;
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }

        //条件匹配器
        //页面名称模糊查询，需要自定义字符串的匹配器实现模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("templateName", ExampleMatcher.GenericPropertyMatchers.contains());

        CmsTemplate cmsTemplate = new CmsTemplate();
        cmsTemplate.setTemplateName(StringUtils.isNotEmpty(templateName) ? templateName : null);

        //创建条件实例
        Example<CmsTemplate> example = Example.of(cmsTemplate, exampleMatcher);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CmsTemplate> cmsTemplatePage = cmsTemplateRepository.findAll(example, pageRequest);
        long total = cmsTemplatePage.getTotalElements();
        List<CmsTemplate> cmsTemplateList = cmsTemplatePage.getContent();
        JSONObject result = new JSONObject();
        result.put("total", total);
        result.put("list", cmsTemplateList);

        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }

    /**
     * 编辑模板页面
     *
     * @param cmsTemplate
     * @return
     */
    @Transactional
    public ResponseResult editTemplate(CmsTemplate cmsTemplate) {
        if (cmsTemplate == null) {
            ExceptionCast.cast(CmsCode.CMS_TEMPLATE_IS_NOTEXISTS);
        }
        CmsTemplate update = this.getTemplate(cmsTemplate.getTemplateId());
        if (update == null) {
            ExceptionCast.cast(CmsCode.CMS_TEMPLATE_IS_NOTEXISTS);
        }
        if (StringUtils.isNotBlank(cmsTemplate.getSiteId())) {
            update.setSiteId(cmsTemplate.getSiteId());
        }
        if (StringUtils.isNotBlank(cmsTemplate.getTemplateName())) {
            update.setTemplateName(cmsTemplate.getTemplateName());
        }
        if (StringUtils.isNotBlank(cmsTemplate.getTemplateParameter())) {
            update.setTemplateParameter(cmsTemplate.getTemplateParameter());
        }
        if (StringUtils.isNotBlank(cmsTemplate.getTemplateFileId())) {
            String templateFileId = update.getTemplateFileId();
            if (StringUtils.isNotBlank(templateFileId)) {
                gridFsTemplate.delete(Query.query(Criteria.where("_id").is(templateFileId)));
            }
            update.setTemplateFileId(cmsTemplate.getTemplateFileId());
        }
        cmsTemplateRepository.save(update);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加末班
     *
     * @param cmsTemplate
     * @return
     */
    @Transactional
    public ResponseResult addTemplate(CmsTemplate cmsTemplate) {
        if (cmsTemplate == null
                || StringUtils.isBlank(cmsTemplate.getSiteId())
                || StringUtils.isBlank(cmsTemplate.getTemplateName())
                || StringUtils.isBlank(cmsTemplate.getTemplateParameter())
                ) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_PARAMS_ISNULL);
        }
        cmsTemplate.setTemplateId(null);
        cmsTemplate.setTemplateFileId(null);
        cmsTemplateRepository.save(cmsTemplate);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     *
     * @param multipartFile
     * @return
     */
    public ResponseResult uploadTemplateFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //保存html文件到GridFS
        ObjectId objectId = gridFsTemplate.store(inputStream, originalFilename);
        //文件id
        String fileId = objectId.toString();
        return new CommonResponseResult(CommonCode.SUCCESS,fileId);
    }

    /**
     *
     * @param id
     * @return
     */
    public ResponseResult delTemplate(String id) {
        CmsTemplate template = this.getTemplate(id);
        //删除关联的页面
        cmsPageRepository.deleteByTemplateId(template.getTemplateId());
        //删除关联的文件
        if (StringUtils.isNotBlank(template.getTemplateFileId())) {
            String templateFileId = template.getTemplateFileId();
            if (StringUtils.isNotBlank(templateFileId)) {
                gridFsTemplate.delete(Query.query(Criteria.where("_id").is(templateFileId)));
            }
        }
        //删除模板
        cmsTemplateRepository.delete(template);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
