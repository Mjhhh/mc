package com.edu.manage_cms.service;

import com.edu.framework.domain.cms.CmsConfig;
import com.edu.framework.domain.cms.CmsConfigModel;
import com.edu.framework.domain.cms.response.CmsCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_cms.dao.CmsConfigRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author mjh
 */
@Service
public class CmsConfigService {

    @Value("${cms.config.carousel}")
    private String carousel;

    @Value("${cms.config.recommend}")
    private String recommend;

    @Value("${cms.config.classification}")
    private String classification;

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    /**
     * 获取CmsConfig
     * @return
     */
    private CmsConfig get(String id) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_CONFIG_IS_NOTEXISTS);
        }
        return optional.get();
    }

    /**
     * 根据id查询cms配置信息
     *
     * @param id
     * @return
     */
    public CmsConfig getConfigById(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_PARAMS_ISNULL);
        }
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 查询所有配置信息
     *
     * @return
     */
    public List<CmsConfig> findList() {
        List<CmsConfig> all = cmsConfigRepository.findAll();
        return all;
    }

    /**
     * 获取轮播图配置信息
     * @return
     */
    public CommonResponseResult getCarousel() {
        CmsConfig cmsConfig = cmsConfigRepository.findByName("轮播图");
        List<String> urls = new ArrayList<>();
        List<CmsConfigModel> model = cmsConfig.getModel();
        if (model == null) {
            return new CommonResponseResult(CommonCode.SUCCESS,null);
        }
        for (CmsConfigModel cmsConfigModel : model) {
            if (StringUtils.isNotBlank(cmsConfigModel.getUrl())) {
                String url = cmsConfigModel.getUrl();
                urls.add(url);
            }
        }
        return new CommonResponseResult(CommonCode.SUCCESS,urls);
    }

    /**
     * 添加轮播图
     * @param url
     * @return
     */
    public ResponseResult addCarousel(String url) {
        CmsConfig cmsConfig = this.get(carousel);
        List<CmsConfigModel> model;
        if (cmsConfig.getModel() == null) {
            model = new ArrayList<>();
        } else {
            model = cmsConfig.getModel();
        }
        CmsConfigModel cmsConfigModel = new CmsConfigModel();
        cmsConfigModel.setUrl(url);
        model.add(cmsConfigModel);

        cmsConfigRepository.save(cmsConfig);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除轮播图
     * @param url
     * @return
     */
    public ResponseResult deleteCarousel(String url) {
        CmsConfig cmsConfig = this.get(carousel);
        if (cmsConfig.getModel() == null) {
            ExceptionCast.cast(CmsCode.CMS_PIC_IS_NOTEXISTS);
        }
        List<CmsConfigModel> model = cmsConfig.getModel();
        for (CmsConfigModel cmsConfigModel : model) {
            if (StringUtils.equals(cmsConfigModel.getUrl(), url)) {
                model.remove(cmsConfigModel);
                break;
            }
        }
        cmsConfigRepository.save(cmsConfig);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
