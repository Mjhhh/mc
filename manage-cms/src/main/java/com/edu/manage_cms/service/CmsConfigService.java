package com.edu.manage_cms.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.framework.domain.cms.CmsConfig;
import com.edu.framework.domain.cms.CmsConfigModel;
import com.edu.framework.domain.cms.CmsCourseConfig;
import com.edu.framework.domain.cms.response.CmsCode;
import com.edu.framework.domain.course.*;
import com.edu.framework.domain.course.ext.CategoryNode;
import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.domain.course.response.CoursePreResult;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_cms.client.CategoryClient;
import com.edu.manage_cms.client.CourseClient;
import com.edu.manage_cms.dao.CmsConfigRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author mjh
 */
@Service
public class CmsConfigService {

    @Value("${cms.config.carousel}")
    private String carousel;

    @Value("${cms.config.newcourse}")
    private String newcourse;

    @Value("${cms.config.recommend}")
    private String recommend;

    @Value("${cms.config.classification}")
    private String classification;

    @Value("${service.imgUrl}")
    private String imgUrl;

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private CourseClient courseClient;

    /**
     * 获取CmsConfig
     *
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
     * 查询子类
     *
     * @param categoryList
     * @param parentId
     * @return
     */
    private List<CmsConfigModel> findChildren(List<Category> categoryList, String parentId) {
        List<CmsConfigModel> cmsConfigModelList = new ArrayList<>();
        for (Category category : categoryList) {
            if (StringUtils.equals(category.getParentid(), parentId)) {
                CmsConfigModel cmsConfigModel = new CmsConfigModel();
                cmsConfigModel.setId(category.getId());
                if (!StringUtils.equals(category.getParentid(), "1")) {
                    cmsConfigModel.setPid(parentId);
                }
                cmsConfigModel.setName(category.getName());
                List<CmsConfigModel> children = this.findChildren(categoryList, category.getId());
                if (children != null) {
                    cmsConfigModel.setChildren(children);
                }

                cmsConfigModelList.add(cmsConfigModel);
            }
        }
        return cmsConfigModelList;
    }

    /**
     * 保存课程配置信息
     * @param ids
     * @param configId
     */
    private void saveCourseConfig(List<String> ids, String configId) {
        if (ids.size() != 5) {
            ExceptionCast.cast(CmsCode.CONFIG_COURSE_IS_ILLEGAL);
        }
        CmsConfig cmsConfig = this.getConfigById(configId);
        if (cmsConfig == null) {
            ExceptionCast.cast(CmsCode.CMS_CONFIG_IS_NOTEXISTS);
        }
        Random random = new Random();
        List<CoursePub> coursePubList = courseClient.findCoursePubByIds(ids);
        List<CmsCourseConfig> cmsCourseConfigs = new ArrayList<>();
        for (CoursePub coursePub : coursePubList) {
            CmsCourseConfig cmsCourseConfig = new CmsCourseConfig();
            BeanUtils.copyProperties(coursePub, cmsCourseConfig);
            cmsCourseConfig.setLearningNum(random.nextInt(9000) + 1000);

            cmsCourseConfigs.add(cmsCourseConfig);
        }
        cmsConfig.setCourseConfigs(cmsCourseConfigs);
        cmsConfigRepository.save(cmsConfig);
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
     *
     * @return
     */
    public CommonResponseResult getCarousel() {
        CmsConfig cmsConfig = cmsConfigRepository.findByName("轮播图");
        List<String> urls = new ArrayList<>();
        List<CmsConfigModel> model = cmsConfig.getModel();
        if (model == null) {
            return new CommonResponseResult(CommonCode.SUCCESS, null);
        }
        for (CmsConfigModel cmsConfigModel : model) {
            if (StringUtils.isNotBlank(cmsConfigModel.getUrl())) {
                String url = cmsConfigModel.getUrl();
                urls.add(url);
            }
        }
        return new CommonResponseResult(CommonCode.SUCCESS, urls);
    }

    /**
     * 添加轮播图
     *
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
        cmsConfigModel.setUrl(imgUrl + url);
        model.add(cmsConfigModel);

        cmsConfigRepository.save(cmsConfig);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除轮播图
     *
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

    /**
     * 获取分类列表
     *
     * @return
     */
    public CommonResponseResult findCategoryList() {
        CategoryNode list = categoryClient.list();
        return new CommonResponseResult(CommonCode.SUCCESS, list);
    }

    /**
     * 添加cms分类配置信息
     *
     * @param ids
     * @return
     */
    public ResponseResult addCmsConfigByCategory(List<String> ids) {
        int num = 0;
        for (String id : ids) {
            String[] split = id.split("-");
            if (split.length == 2) {
                num++;
            }
        }
        if (num != 10) {
            ExceptionCast.cast(CmsCode.CMS_CATEGORY_IS_OVERSTEP);
        }
        CmsConfig cmsConfig = this.getConfigById(classification);
        if (cmsConfig == null) {
            ExceptionCast.cast(CmsCode.CMS_CONFIG_IS_NOTEXISTS);
        }
        List<Category> categoryList = categoryClient.findByIds(ids);
        List<CmsConfigModel> children = this.findChildren(categoryList, "1");
        cmsConfig.setModel(children);
        cmsConfigRepository.save(cmsConfig);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取cms分类配置信息
     *
     * @return
     */
    public CommonResponseResult findCategoryCmsConfig() {
        CmsConfig cmsConfig = this.getConfigById(classification);
        if (cmsConfig.getModel() == null) {
            return new CommonResponseResult(CommonCode.SUCCESS, null);
        }
        List<String> ids = new ArrayList<>();
        List<CmsConfigModel> allModels = new ArrayList<>();
        List<CmsConfigModel> models = cmsConfig.getModel();
        allModels.addAll(models);
        for (CmsConfigModel cmsConfigModel : models) {
            if (cmsConfigModel.getChildren() != null) {
                List<CmsConfigModel> children = cmsConfigModel.getChildren();
                allModels.addAll(children);
            }
        }
        for (CmsConfigModel cmsConfigModel : allModels) {
            if (cmsConfigModel.getId().split("-").length == 3) {
                ids.add(cmsConfigModel.getId());
            }
        }
        return new CommonResponseResult(CommonCode.SUCCESS, ids);
    }

    /**
     * 添加课程分类信息
     * @param category
     * @return
     */
    public ResponseResult addCourseCategory(Category category) {
        return categoryClient.addCourseCategory(category);
    }

    /**
     * 删除课程分类信息
     * @param id
     * @return
     */
    public ResponseResult delCourseCategory(String id) {
        return categoryClient.delCourseCategory(id);
    }

    /**
     * 查询课程列表
     * @return
     */
    public CommonResponseResult findCourseList(int page, int size) {
        CoursePreResult coursePreResult = courseClient.findCoursePreviewList(page, size);
        List<CourseView> courseViewList = coursePreResult.getCourseViewList();
        JSONArray result = new JSONArray();
        for (CourseView courseView : courseViewList) {
            JSONObject object = new JSONObject();
            Random random = new Random();
            CourseBase courseBase = courseView.getCourseBase();
            CourseMarket courseMarket = courseView.getCourseMarket();
            CoursePic coursePic = courseView.getCoursePic();
            if (coursePic == null) {
                object.put("url", null);
            } else {
                object.put("url", coursePic.getPic());
            }
            object.put("courseId", courseBase.getId());
            object.put("name", courseBase.getName());
            object.put("grade", courseBase.getGrade());
            object.put("learningNum", random.nextInt(9000) + 1000);
            object.put("price", courseMarket.getPrice());
            object.put("priceOld", courseMarket.getPriceOld());
            object.put("description", courseBase.getDescription());

            result.add(object);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", result);
        data.put("total", coursePreResult.getTotal());

        return new CommonResponseResult(CommonCode.SUCCESS, data);
    }

    /**
     * 添加cms课程推荐配置信息
     * @param ids
     * @return
     */
    public ResponseResult addCmsCourseRecomment(List<String> ids) {
        this.saveCourseConfig(ids, recommend);
        return ResponseResult.SUCCESS();
    }

    /**
     * 添加cms新上好课配置信息
     * @param ids
     * @return
     */
    public ResponseResult addCmsNewCourse(List<String> ids) {
        this.saveCourseConfig(ids, newcourse);
        return ResponseResult.SUCCESS();
    }
}
