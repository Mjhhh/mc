package com.edu.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.response.CmsPageResult;
import com.edu.framework.domain.course.*;
import com.edu.framework.domain.course.ext.*;
import com.edu.framework.domain.course.request.CourseListRequest;
import com.edu.framework.domain.course.response.CourseCode;
import com.edu.framework.domain.course.response.CoursePreResult;
import com.edu.framework.domain.course.response.CourseResult;
import com.edu.framework.domain.course.response.TeachplanMediaPub;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.response.McCompanyResult;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.*;
import com.edu.framework.utils.McOauth2Util;
import com.edu.manage_course.client.CmsPageClient;
import com.edu.manage_course.client.FileSystemClient;
import com.edu.manage_course.client.UserClient;
import com.edu.manage_course.dao.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CourseService {

    @Value("${course‐publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course‐publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course‐publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course‐publish.siteId}")
    private String publish_siteId;
    @Value("${course‐publish.templateId}")
    private String publish_templateId;
    @Value("${course‐publish.previewUrl}")
    private String previewUrl;

    @Resource
    TeachplanMapper teachplanMapper;

    @Resource
    CourseMapper courseMapper;

    @Autowired
    CourseBaseRepository courseBaseRepository;

    @Autowired
    TeachplanRepository teachplanRepository;

    @Autowired
    CourseMarketRepository courseMarketRepository;

    @Autowired
    CoursePicRepository coursePicRepository;

    @Autowired
    CoursePubRepository coursePubRepository;

    @Autowired
    CmsPageClient cmsPageClient;

    @Autowired
    TeachplanMediaRepository teachplanMediaRepository;

    @Autowired
    TeachplanMediaPubRepository teachplanMediaPubRepository;

    @Autowired
    FileSystemClient fileSystemClient;

    @Autowired
    CourseTeacherRepository courseTeacherRepository;

    @Autowired
    UserClient userClient;

    @Resource
    CourseBaseMapper courseBaseMapper;

    @Resource
    CoursePubMapper coursePubMapper;

    @Autowired
    CourseEvaluateRepository courseEvaluateRepository;

    @Autowired
    CourseQuestionRepository courseQuestionRepository;

    @Autowired
    CourseAnswerRepository courseAnswerRepository;

    /**
     * 获取用户id
     *
     * @return
     */
    private String getUserId() {
        HttpServletRequest request = this.getRequest();
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (StringUtils.isBlank(userJwt.getId())) {
            ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        }
        return userJwt.getId();
    }

    /**
     * 获取课程视频映射记录
     *
     * @param id
     * @return
     */
    private TeachplanMedia getTeachplanMedia(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Optional<TeachplanMedia> optional = teachplanMediaRepository.findById(id);
        return optional.orElse(null);
    }

    private CourseEvaluate getCourseEvaluate(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Optional<CourseEvaluate> optional = courseEvaluateRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 获取课程根结点，如果没有则添加根结点
     *
     * @param courseId 课程id
     * @return 课程根结点
     */
    private String getTeachplanRoot(String courseId) {
        //获取课程基本信息
        CourseBase courseBase = this.getCourseBase(courseId);
        //去除课程计划根结点
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if (teachplanList == null || teachplanList.isEmpty()) {
            //新增一个根结点
            Teachplan root = new Teachplan();
            root.setCourseid(courseId);
            root.setPname(courseBase.getName());
            root.setParentid("0");
            root.setGrade(Teachplan.GRADE_LEVEL_ONE);
            root.setStatus(Teachplan.STATUS_NOT_RELEASE);
            teachplanRepository.save(root);
            return root.getId();
        }
        Teachplan teachplan = teachplanList.get(0);
        return teachplan.getId();
    }

    /**
     * 获取课程讲师
     *
     * @param id
     * @return
     */
    private CourseTeacher getCourseTeacher(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CourseCode.COURSE_PARAMS_IS_NULL);
        }
        Optional<CourseTeacher> optional = courseTeacherRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 获取公司ID
     * @return
     */
    private String getCompanyId() {
        HttpServletRequest request = this.getRequest();
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (userJwt == null) {
            ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        }
        if (StringUtils.isBlank(userJwt.getCompanyId())) {
            ExceptionCast.cast(CommonCode.MISS_COMPANY_ID);
        }
        return userJwt.getCompanyId();
    }

    /**
     * 查询课程信息
     *
     * @param courseId 课程ID
     * @return 课程信息
     */
    private CourseBase getCourseBase(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        //查询课程信息
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        return optional.orElse(null);
    }

    /**
     * 获取课程计划信息
     *
     * @param teachplanId
     * @return
     */
    private Teachplan getTeachplan(String teachplanId) {
        if (StringUtils.isBlank(teachplanId)) {
            ExceptionCast.cast(CourseCode.COURSE_PARAMS_IS_NULL);
        }
        Optional<Teachplan> optional = teachplanRepository.findById(teachplanId);
        return optional.orElse(null);
    }

    /**
     * 查询课程营销信息
     *
     * @param courseId 课程ID
     * @return 课程营销信息
     */
    private CourseMarket getCourseMarket(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        return optional.orElse(null);
    }

    /**
     * 查询课程图片信息
     *
     * @param courseId 课程ID
     * @return 课程图片信息
     */
    private CoursePic getCoursePic(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        return optional.orElse(null);
    }

    /**
     * 获取课程索引表
     *
     * @param courseId 课程编号
     * @return 课程索引表
     */
    private CoursePub getCoursePub(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        Optional<CoursePub> optional = coursePubRepository.findById(courseId);
        return optional.orElse(null);
    }

    /**
     * 设置CmsPage默认数据
     *
     * @param courseId
     * @return
     */
    private CmsPage setCmsPageAttribute(String courseId) {
        CourseBase courseBase = this.getCourseBase(courseId);
        //发布课程预览页面
        CmsPage cmsPage = new CmsPage();
        //课程预览站点
        cmsPage.setSiteId(publish_siteId);
        //模板
        cmsPage.setTemplateId(publish_templateId);
        //页面名称
        cmsPage.setPageName(courseId + ".html");
        //页面别名
        cmsPage.setPageAliase(courseBase.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre + courseId);
        //页面类型
        cmsPage.setPageType("1");
        //创建时间
        cmsPage.setPageCreateTime(new Date());
        return cmsPage;
    }

    /**
     * 更新课程发布状态
     *
     * @param courseId 课程编号
     * @param status   课程状态码
     * @return 课程信息
     */
    private CourseBase saveCourseBaseStatus(String courseId, String status) {
        CourseBase courseBase = this.getCourseBase(courseId);
        //更新发布状态
        courseBase.setStatus(status);
        return courseBaseRepository.save(courseBase);
    }

    /**
     * 保存课程计划媒资信息到待索引表
     *
     * @param courseId
     */
    private void saveTeachplanMediaPub(String courseId) {
        //查询课程媒资信息
        List<TeachplanMedia> teachplanMediaList = teachplanMediaRepository.findByCourseId(courseId);
        //删除课程计划媒资信息待索引表
        teachplanMediaPubRepository.deleteByCourseId(courseId);
        List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<>();
        for (TeachplanMedia teachplanMedia : teachplanMediaList) {
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            BeanUtils.copyProperties(teachplanMedia, teachplanMediaPub);
            teachplanMediaPubList.add(teachplanMediaPub);
        }
        teachplanMediaPubRepository.saveAll(teachplanMediaPubList);
    }

    /**
     * 保存课程信息聚合
     *
     * @param courseId  课程编号
     * @param coursePub 课程信息聚合
     * @return 课程信息聚合
     */
    private CoursePub saveCoursePub(String courseId, CoursePub coursePub) {
        if (StringUtils.isBlank(courseId)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        CoursePub coursePubNew = this.getCoursePub(courseId);
        if (coursePubNew == null) {
            coursePubNew = new CoursePub();
        }
        BeanUtils.copyProperties(coursePub, coursePubNew);
        //设置主键
        coursePubNew.setId(courseId);
        //更新时间戳
        coursePub.setTimestamp(new Date());
        //发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        coursePub.setPubTime(simpleDateFormat.format(new Date()));
        coursePubRepository.save(coursePub);
        return coursePub;
    }

    /**
     * 创建coursePub对象
     *
     * @param courseId 课程编号
     * @return CoursePub
     */
    private CoursePub createCoursePub(String courseId) {
        CoursePub coursePub = new CoursePub();
        //基础信息
        CourseBase courseBase = this.getCourseBase(courseId);
        if (courseBase == null) {
            courseBase = new CourseBase();
        }
        BeanUtils.copyProperties(courseBase, coursePub);
        //图片信息
        CoursePic coursePic = this.getCoursePic(courseId);
        if (coursePic == null) {
            coursePic = new CoursePic();
        }
        BeanUtils.copyProperties(coursePic, coursePub);
        //课程营销信息
        CourseMarket courseMarket = this.getCourseMarket(courseId);
        if (courseMarket == null) {
            courseMarket = new CourseMarket();
        }
        BeanUtils.copyProperties(courseMarket, coursePub);
        //课程计划
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        //将课程计划转成JSON
        String teachplanNodeString = JSON.toJSONString(teachplanNode);
        coursePub.setId(courseId);
        coursePub.setTeachplan(teachplanNodeString);
        return coursePub;
    }

    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 查询课程计划
     *
     * @param courseId 课程id
     * @return 课程计划列表
     */
    public CommonResponseResult findTeachplanList(String courseId) {
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return new CommonResponseResult(CommonCode.SUCCESS, teachplanNode);
    }

    /**
     * 添加课程计划
     *
     * @param teachplan 课程计划实体
     * @return 提示信息
     */
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {
        //校验课程id和课程计划名称
        if (teachplan == null || StringUtils.isEmpty(teachplan.getCourseid()) || StringUtils.isEmpty(teachplan.getPname())) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }

        //取出课程id
        String courseid = teachplan.getCourseid();
        //取出上级结点id
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)) {
            //如果父结点为空则获取根结点
            parentid = this.getTeachplanRoot(courseid);
        }
        //取出父结点信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //父结点
        Teachplan teachplanParent = optional.get();
        //父结点级别
        String parentGrade = teachplanParent.getGrade();
        //设置子结点
        teachplan.setParentid(parentid);
        teachplan.setStatus(Teachplan.STATUS_NOT_RELEASE);
        //子结点的级别，根据父结点来判断
        if (StringUtils.equals(parentGrade, Teachplan.GRADE_LEVEL_ONE)) {
            teachplan.setGrade(Teachplan.GRADE_LEVEL_TWO);
        } else if (StringUtils.equals(parentGrade, Teachplan.GRADE_LEVEL_TWO)) {
            teachplan.setGrade(Teachplan.GRADE_LEVEL_THREE);
        }
        //设置课程id
        teachplan.setCourseid(teachplanParent.getCourseid());
        teachplanRepository.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest) {
        HttpServletRequest request = this.getRequest();
        //调用工具类取出用户信息
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        //进行分页
        if (courseListRequest == null) {
            courseListRequest = new CourseListRequest();
        }
        //设置公司id
        courseListRequest.setCompanyId(userJwt.getCompanyId());
        if (page <= 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        //设置分页
        PageHelper.startPage(page, size);
        //分页查询
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        //查询列表
        List<CourseInfo> result = courseListPage.getResult();
        //总记录数
        long total = courseListPage.getTotal();
        //查询结果集
        QueryResult<CourseInfo> data = new QueryResult<>();
        data.setList(result);
        data.setTotal(total);
        return new QueryResponseResult(CommonCode.SUCCESS, data);
    }

    /**
     * 添加课程
     *
     * @param courseBase 课程信息
     * @return 提示信息
     */
    @Transactional
    public ResponseResult addCourseBase(CourseBase courseBase) {
        //课程默认设置为未发布
        String companyId = this.getCompanyId();
        courseBase.setCompanyId(companyId);
        courseBase.setStatus("202001");
        courseBaseRepository.save(courseBase);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取课程基本信息
     *
     * @param courseId 课程ID
     * @return 课程基本信息
     */
    public CommonResponseResult getCourseBaseById(String courseId) {
        //查询课程信息
        CourseBase courseBase = this.getCourseBase(courseId);
        if (courseBase == null) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_IS_NULL);
        }
        return new CommonResponseResult(CommonCode.SUCCESS, courseBase);
    }

    /**
     * 更新课程基础信息
     *
     * @param id         课程ID
     * @param courseBase 更新的参数
     * @return 提示信息
     */
    @Transactional
    public ResponseResult updateCourseBase(String id, CourseBase courseBase) {
        CourseBase course = this.getCourseBase(id);
        if (course == null) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_IS_NULL);
        }
        //修改课程
        course.setName(courseBase.getName());
        course.setMt(courseBase.getMt());
        course.setSt(courseBase.getSt());
        course.setGrade(courseBase.getGrade());
        course.setStudymodel(courseBase.getStudymodel());
        course.setUsers(courseBase.getUsers());
        course.setDescription(courseBase.getDescription());
        courseBaseRepository.save(course);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取课程营销信息
     *
     * @param courseId 课程ID
     * @return 课程营销信息
     */
    public CommonResponseResult getCourseMarketById(String courseId) {
        CourseMarket courseMarket = this.getCourseMarket(courseId);
        return new CommonResponseResult(CommonCode.SUCCESS, courseMarket);
    }

    /**
     * 更新课程营销信息
     *
     * @param id           课程ID
     * @param courseMarket 修改的课程营销参数
     * @return 操作结果
     */
    @Transactional
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket) {
        if (StringUtils.equals(courseMarket.getCharge(), "203002")
                && (courseMarket.getPrice() == null
                || courseMarket.getPriceOld() == null)) {
            ExceptionCast.cast(CourseCode.COURSE_PRICE_IS_NULL);
        }
        CourseMarket market = this.getCourseMarket(id);
        if (market != null) {
            market.setCharge(courseMarket.getCharge());
            market.setStartTime(courseMarket.getStartTime());
            market.setEndTime(courseMarket.getEndTime());
            market.setPrice(courseMarket.getPrice());
            market.setPriceOld(courseMarket.getPriceOld());
            market.setQq(courseMarket.getQq());
            market.setValid(courseMarket.getValid());
        } else {
            //添加课程营销信息
            market = new CourseMarket();
            BeanUtils.copyProperties(courseMarket, market);
            //设置课程id
            market.setId(id);
        }
        courseMarketRepository.save(market);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 保存课程图片
     *
     * @param courseId 课程编号
     * @param pic      图片地址
     * @return 操作信息
     */
    @Transactional
    public ResponseResult saveCoursePic(String courseId, String pic) {
        if (StringUtils.isBlank(courseId) || StringUtils.isBlank(pic)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        //查询是否已经存在课程图片
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        CoursePic coursePic = null;
        if (optional.isPresent()) {
            coursePic = optional.get();
        }
        //如果没有课程图片则创建对象
        if (coursePic == null) {
            coursePic = new CoursePic();
        }
        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);

        //保存课程图片
        coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 查询课程图片
     *
     * @param courseId 课程编号
     * @return 课程图片信息
     */
    public CommonResponseResult findCoursepic(String courseId) {
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        return new CommonResponseResult(CommonCode.SUCCESS, optional.orElse(null));
    }

    /**
     * 删除课程图片
     *
     * @param courseId 课程编号
     * @return 操作信息
     */
    @Transactional
    public ResponseResult deleteCoursePic(String courseId) {
        //执行删除，返回1表示删除成功，返回0表示删除失败
        long result = coursePicRepository.deleteByCourseid(courseId);
        if (result > 0) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    public CourseView getCoruseView(String courseId) {
        CourseView courseView = new CourseView();
        //查询课程基本信息
        CourseBase courseBase = this.getCourseBase(courseId);
        if (courseBase != null) {
            courseView.setCourseBase(courseBase);
        }
        //查询课程营销信息
        CourseMarket courseMarket = this.getCourseMarket(courseId);
        if (courseMarket != null) {
            courseView.setCourseMarket(courseMarket);
        }
        //查询课程图片信息
        CoursePic coursePic = this.getCoursePic(courseId);
        if (coursePic != null) {
            courseView.setCoursePic(coursePic);
        }
        //查询课程计划信息
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        if (teachplanNode != null) {
            courseView.setTeachplanNode(teachplanNode);
        }
        //查询课程讲师信息
        CourseTeacher courseTeacher = this.getCourseTeacher(courseId);
        if (courseTeacher != null) {
            courseView.setCourseTeacher(courseTeacher);
        }
        //查询课程组织信息
        McCompanyResult mcCompanyResult = userClient.getCompany(courseBase.getCompanyId());
        if (mcCompanyResult.isSuccess()) {
            courseView.setMcCompany(mcCompanyResult.getData());
        }
        return courseView;
    }

    /**
     * 课程预览页面
     *
     * @param courseId 课程编号
     * @return 课程预览链接
     */
    @Transactional
    public CourseResult preview(String courseId) {
        CmsPage cmsPage = this.setCmsPageAttribute(courseId);
        //远程请求cms保存页面信息
        CmsPageResult cmsPageResult = cmsPageClient.save(cmsPage);
        if (!cmsPageResult.isSuccess()) {
            return new CourseResult(CommonCode.FAIL, null);
        }
        //页面id
        String pageId = cmsPageResult.getCmsPage().getPageId();
        //页面url
        String pageUrl = previewUrl + pageId;
        return new CourseResult(CommonCode.SUCCESS, pageUrl);
    }

    @Transactional
    public CourseResult publish(String courseId) {
        //设置课程默认参数
        CmsPage cmsPage = this.setCmsPageAttribute(courseId);
        //一键发布页面
        CommonResponseResult commonResponseResult = cmsPageClient.postPageQuick(cmsPage);
//        if (!commonResponseResult.isSuccess()) {
//            ExceptionCast.cast(CmsCode.CMS_PAGE_POST_FAIL);
//        }
        //更新课程状态-审核中
        CourseBase courseBase = this.saveCourseBaseStatus(courseId, "202004");
//        //创建课程索引信息
//        CoursePub coursePub = this.createCoursePub(courseId);
//        //向数据库保存课程索引信息
//        CoursePub newCoursePub = this.saveCoursePub(courseId, coursePub);
//        if (newCoursePub == null) {
//            //创建课程索引信息失败
//            ExceptionCast.cast(CourseCode.COURSE_PUB_IS_NULL);
//        }
//        //保存课程计划媒资信息到待索引表
//        this.saveTeachplanMediaPub(courseId);
        //页面url
        String pageUrl = (String) commonResponseResult.getData();
        return new CourseResult(CommonCode.SUCCESS, pageUrl);
    }

    @Transactional
    public ResponseResult savemedia(TeachplanMedia teachplanMedia) {
        if (teachplanMedia == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        String teachplanId = teachplanMedia.getTeachplanId();
        //获取课程计划
        Optional<Teachplan> teachplanOptional = teachplanRepository.findById(teachplanId);
        if (!teachplanOptional.isPresent()) {
            ExceptionCast.cast(CourseCode.COURSE_MARKET_IS_NULL);
        }
        Teachplan teachplan = teachplanOptional.get();
        //只允许叶子节点课程选择视频
        String grade = teachplan.getGrade();
        if (teachplan == null || !StringUtils.equals(grade, "3")) {
            ExceptionCast.cast(CourseCode.COURSE_MEDIA_TEACHPLAN_GRADE_ERROR);
        }
        TeachplanMedia one = null;
        Optional<TeachplanMedia> teachplanMediaOptional = teachplanMediaRepository.findById(teachplanId);
        if (!teachplanMediaOptional.isPresent()) {
            one = new TeachplanMedia();
        } else {
            one = teachplanMediaOptional.get();
        }
        //保存媒资信息与课程计划信息
        one.setTeachplanId(teachplanId);
        one.setCourseId(teachplanMedia.getCourseId());
        one.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
        one.setMediaId(teachplanMedia.getMediaId());
        one.setMediaUrl(teachplanMedia.getMediaUrl());
        teachplanMediaRepository.save(one);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 查询课程封面预览信息
     *
     * @param page
     * @param size
     * @return
     */
    public CoursePreResult findCoursePreviewList(int page, int size) {
        page = page - 1;
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        //设置分页
        PageRequest pageRequest = PageRequest.of(page, size);
        org.springframework.data.domain.Page<CourseBase> all = courseBaseRepository.findAll(pageRequest);
        List<CourseBase> courseBaseList = all.getContent();
        long total = all.getTotalElements();

        List<CourseView> courseViewList = new ArrayList<>();
        for (CourseBase courseBase : courseBaseList) {
            CourseView courseView = new CourseView();
            courseView.setCourseBase(courseBase);
            CoursePic coursePic = this.getCoursePic(courseBase.getId());
            courseView.setCoursePic(coursePic);
            CourseMarket courseMarket = this.getCourseMarket(courseBase.getId());
            courseView.setCourseMarket(courseMarket);

            courseViewList.add(courseView);
        }
        return new CoursePreResult(CommonCode.SUCCESS, courseViewList, total);
    }

    /**
     * 删除课程基础信息
     *
     * @param courseId
     * @return
     */
    @Transactional
    public ResponseResult delCourseBase(String courseId) {
        CourseBase courseBase = this.getCourseBase(courseId);
        if (courseBase == null) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_IS_NULL);
        }
        //删除课程营销计划
        CourseMarket courseMarket = this.getCourseMarket(courseId);
        if (courseMarket != null) {
            courseMarketRepository.deleteById(courseId);
        }
        //删除课程封面
        CoursePic coursePic = this.getCoursePic(courseId);
        if (coursePic != null) {
            String fileId = coursePic.getPic();
            fileSystemClient.delete(fileId);
            coursePicRepository.deleteByCourseid(courseId);
        }
        CoursePub coursePub = this.getCoursePub(courseId);
        if (coursePub != null) {
            //删除课程聚合信息
            coursePubRepository.deleteById(courseId);
        }
        List<Teachplan> teachplanList = teachplanRepository.findByCourseid(courseId);
        if (!CollectionUtils.isEmpty(teachplanList)) {
            //删除课程计划
            teachplanRepository.deleteByCourseid(courseId);
        }
        List<TeachplanMedia> teachplanMediaList = teachplanMediaRepository.findByCourseId(courseId);
        if (!CollectionUtils.isEmpty(teachplanMediaList)) {
            //删除课程媒资信息
            teachplanMediaRepository.deleteByCourseId(courseId);
        }
        List<TeachplanMediaPub> teachplanMediaPubList = teachplanMediaPubRepository.findByCourseId(courseId);
        if (!CollectionUtils.isEmpty(teachplanMediaPubList)) {
            //删除课程媒资聚合信息
            teachplanMediaPubRepository.deleteByCourseId(courseId);
        }
        //删除课程基本信息
        courseBaseRepository.deleteById(courseId);
        //删除cms页面
        cmsPageClient.deleteByCourseId(courseId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据id获取课程计划
     *
     * @param teachplanId
     * @return
     */
    public CommonResponseResult findTeachplanById(String teachplanId) {
        Teachplan teachplan = this.getTeachplan(teachplanId);
        return new CommonResponseResult(CommonCode.SUCCESS, teachplan);
    }

    /**
     * 添加课程讲师
     *
     * @param courseTeacher
     * @return
     */
    public ResponseResult addCourseTeacher(CourseTeacher courseTeacher) {
        if (courseTeacher == null) {
            ExceptionCast.cast(CourseCode.COURSE_PARAMS_IS_NULL);
        }
        if (StringUtils.isBlank(courseTeacher.getId())) {
            ExceptionCast.cast(CourseCode.COURSE_PARAMS_IS_NULL);
        }
        courseTeacherRepository.save(courseTeacher);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 查询课程讲师
     *
     * @param courseId
     * @return
     */
    public CommonResponseResult findcourseTeacher(String courseId) {
        CourseTeacher courseTeacher = this.getCourseTeacher(courseId);
        return new CommonResponseResult(CommonCode.SUCCESS, courseTeacher);
    }

    /**
     * 删除课程视频映射记录
     *
     * @param mediaId
     * @return
     */
    @Transactional
    public ResponseResult delTeachplanMedia(String mediaId) {
        TeachplanMedia teachplanMedia = teachplanMediaRepository.findByMediaId(mediaId);
        if (teachplanMedia == null) {
            ExceptionCast.cast(CommonCode.OBJECT_IS_NOT_EXISTS);
        }
        teachplanMediaRepository.delete(teachplanMedia);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据课程id查询课程
     *
     * @param ids
     * @return
     */
    public CommonResponseResult findCourseListByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        List<CoursePub> coursePubs = coursePubMapper.selectListByIds(ids);
        return new CommonResponseResult(CommonCode.SUCCESS, coursePubs);
    }

    /**
     * 添加课程评论
     *
     * @param courseEvaluate
     * @return
     */
    public ResponseResult addCourseEvaluate(CourseEvaluate courseEvaluate) {
        if (courseEvaluate == null
                || StringUtils.isBlank(courseEvaluate.getContent())
                || StringUtils.isBlank(courseEvaluate.getCourseId())
                || courseEvaluate.getScore() == null
                || StringUtils.isBlank(courseEvaluate.getUserId())) {
            ExceptionCast.cast(CourseCode.COURSE_EVALUATE_IS_NULL);
        }
        courseEvaluate.setCreateTime(new Date());
        courseEvaluateRepository.save(courseEvaluate);
        return ResponseResult.SUCCESS();
    }

    /**
     * @param courseId
     * @return
     */
    public CommonResponseResult findCourseEvaluateList(String courseId) {
        if (StringUtils.isBlank(courseId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        List<CourseEvaluate> courseEvaluateList = courseEvaluateRepository.selectByCourseId(courseId);
        List<String> userIds = new ArrayList<>();
        for (CourseEvaluate courseEvaluate : courseEvaluateList) {
            String userId = courseEvaluate.getUserId();
            userIds.add(userId);
        }
        List<McUser> mcUserList = new ArrayList<>();
        if (userIds.size() > 0) {
            mcUserList = userClient.findUserListByIds(userIds);
        }
        List<CourseEvaluateExt> courseEvaluateExts = new ArrayList<>();
        double totalScore = 0;
        for (CourseEvaluate courseEvaluate : courseEvaluateList) {
            for (McUser mcUser : mcUserList) {
                if (StringUtils.equals(courseEvaluate.getUserId(), mcUser.getId())) {
                    CourseEvaluateExt courseEvaluateExt = new CourseEvaluateExt();
                    BeanUtils.copyProperties(courseEvaluate, courseEvaluateExt);
                    if (StringUtils.isNotBlank(mcUser.getName())) {
                        courseEvaluateExt.setUsername(mcUser.getName());
                    }
                    if (StringUtils.isNotBlank(mcUser.getUserpic())) {
                        courseEvaluateExt.setUserpic("http://mjh0523.xyz/" + mcUser.getUserpic());
                    }
                    courseEvaluateExts.add(courseEvaluateExt);
                    totalScore += courseEvaluate.getScore();
                    break;
                }
            }
        }
        int total = courseEvaluateExts.size();
        DecimalFormat df = new DecimalFormat("0.0");
        JSONObject result = new JSONObject();
        result.put("total", total);
        result.put("list", courseEvaluateExts);
        result.put("totalScore", df.format(totalScore));
        result.put("averageScore", df.format(totalScore / total));
        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }

    /**
     * 删除课程评论
     *
     * @param id
     * @return
     */
    public ResponseResult delCourseEvaluate(String id) {
        CourseEvaluate courseEvaluate = this.getCourseEvaluate(id);
        if (courseEvaluate == null) {
            ExceptionCast.cast(CourseCode.COURSE_EVALUATE_IS_NULL);
        }
        //先判断权限
        HttpServletRequest request = this.getRequest();
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        String companyId = userJwt.getCompanyId();
        String courseId = courseEvaluate.getCourseId();
        CourseBase courseBase = this.getCourseBase(courseId);
        if (!StringUtils.equals(courseBase.getCompanyId(), companyId)) {
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        courseEvaluateRepository.delete(courseEvaluate);
        return ResponseResult.SUCCESS();
    }

    /**
     * 根据id查找课程索引
     * @param ids
     * @return
     */
    public List<CoursePub> findCoursePubByIds(List<String> ids) {
        List<CoursePub> coursePubs = coursePubMapper.selectListByIds(ids);
        return coursePubs;
    }

    /**
     * 课程审核通过
     * @param courseId
     * @return
     */
    @Transactional
    public ResponseResult updateCourseBaseCheckPass(String courseId) {
        CourseBase course = this.getCourseBase(courseId);
        if (course == null) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_IS_NULL);
        }
        //课程审核通过-发布
        course.setStatus("202002");
        courseBaseRepository.save(course);
        //创建课程索引信息
        CoursePub coursePub = this.createCoursePub(courseId);
        //向数据库保存课程索引信息
        CoursePub newCoursePub = this.saveCoursePub(courseId, coursePub);
        if (newCoursePub == null) {
            //创建课程索引信息失败
            ExceptionCast.cast(CourseCode.COURSE_PUB_IS_NULL);
        }
        //保存课程计划媒资信息到待索引表
        this.saveTeachplanMediaPub(courseId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 课程问题列表
     * @param courseId
     * @return
     */
    public CommonResponseResult findCourseQuestionList(String courseId) {
        if (StringUtils.isBlank(courseId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }

        List<CourseQuestion> courseQuestionList = courseQuestionRepository.selectByCourseId(courseId);

        List<String> userIds = new ArrayList<>();
        for (CourseQuestion courseQuestion : courseQuestionList) {
            String userId = courseQuestion.getUserId();
            userIds.add(userId);
        }
        List<McUser> mcUserList = new ArrayList<>();
        if (userIds.size() > 0) {
            mcUserList = userClient.findUserListByIds(userIds);
        }
        List<CourseQuestionExt> courseQuestionExts = new ArrayList<>();
        Random random = new Random();
        for (CourseQuestion courseQuestion : courseQuestionList) {
            for (McUser mcUser : mcUserList) {
                if (StringUtils.equals(courseQuestion.getUserId(), mcUser.getId())) {
                    CourseQuestionExt courseQuestionExt = new CourseQuestionExt();
                    BeanUtils.copyProperties(courseQuestion, courseQuestionExt);
                    if (StringUtils.isNotBlank(mcUser.getName())) {
                        courseQuestionExt.setUsername(mcUser.getName());
                    }
                    if (StringUtils.isNotBlank(mcUser.getUserpic())) {
                        courseQuestionExt.setUserpic("http://mjh0523.xyz/" + mcUser.getUserpic());
                    }
                    List<CourseAnswer> courseAnswerList = courseAnswerRepository.findByQuestionId(courseQuestion.getId());
                    courseQuestionExt.setAnswerTotal(courseAnswerList.size());
                    courseQuestionExt.setLookNum(random.nextInt(900)+100);

                    courseQuestionExts.add(courseQuestionExt);
                    break;
                }
            }
        }

        return new CommonResponseResult(CommonCode.SUCCESS, courseQuestionExts);
    }

    /**
     * 添加课程问题
     * @param courseQuestion
     * @return
     */
    public ResponseResult addCourseQuestion(CourseQuestion courseQuestion) {
        if (courseQuestion == null
                || StringUtils.isBlank(courseQuestion.getUserId())
                || StringUtils.isBlank(courseQuestion.getCourseId())) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        courseQuestion.setCreateTime(new Date());
        courseQuestionRepository.save(courseQuestion);
        return ResponseResult.SUCCESS();
    }

    /**
     * 删除课程评论
     * @param id
     * @return
     */
    public ResponseResult delCourseQuestion(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        String userId = this.getUserId();
        Optional<CourseQuestion> optional = courseQuestionRepository.findById(id);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.OBJECT_IS_NOT_EXISTS);
        }
        CourseQuestion courseQuestion = optional.get();
        if (!StringUtils.equals(courseQuestion.getUserId(), userId)) {
            ExceptionCast.cast(CourseCode.YOU_NOT_HAVE_AUTHORITY);
        }
        courseQuestionRepository.deleteById(id);
        return ResponseResult.SUCCESS();
    }

    /**
     * 课程回答列表
     * @param questionId
     * @return
     */
    public CommonResponseResult findCourseAnswerList(String questionId) {
        if (StringUtils.isBlank(questionId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }

        List<CourseAnswer> courseAnswerList = courseAnswerRepository.findByQuestionId(questionId);

        List<String> userIds = new ArrayList<>();
        for (CourseAnswer courseAnswer : courseAnswerList) {
            String userId = courseAnswer.getUserId();
            userIds.add(userId);
        }
        List<McUser> mcUserList = new ArrayList<>();
        if (userIds.size() > 0) {
            mcUserList = userClient.findUserListByIds(userIds);
        }
        List<CourseAnswerExt> courseAnswerExts = new ArrayList<>();
        for (CourseAnswer courseAnswer : courseAnswerList) {
            for (McUser mcUser : mcUserList) {
                if (StringUtils.equals(courseAnswer.getUserId(), mcUser.getId())) {
                    CourseAnswerExt courseAnswerExt = new CourseAnswerExt();
                    BeanUtils.copyProperties(courseAnswer, courseAnswerExt);
                    if (StringUtils.isNotBlank(mcUser.getName())) {
                        courseAnswerExt.setUsername(mcUser.getName());
                    }
                    if (StringUtils.isNotBlank(mcUser.getUserpic())) {
                        courseAnswerExt.setUserpic("http://mjh0523.xyz/" + mcUser.getUserpic());
                    }

                    courseAnswerExts.add(courseAnswerExt);
                    break;
                }
            }
        }

        return new CommonResponseResult(CommonCode.SUCCESS, courseAnswerExts);
    }

    /**
     * 添加课程回答
     * @param courseAnswer
     * @return
     */
    public ResponseResult addCourseAnswer(CourseAnswer courseAnswer) {
        if (courseAnswer == null
                || StringUtils.isBlank(courseAnswer.getQuestionId())
                || StringUtils.isBlank(courseAnswer.getUserId())) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        courseAnswer.setCreateTime(new Date());
        courseAnswerRepository.save(courseAnswer);
        return ResponseResult.SUCCESS();
    }

    /**
     * 删除课程回答
     * @param id
     * @return
     */
    public ResponseResult delCourseAnswer(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        String userId = this.getUserId();
        Optional<CourseAnswer> optional = courseAnswerRepository.findById(id);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.OBJECT_IS_NOT_EXISTS);
        }
        CourseAnswer courseAnswer = optional.get();
        if (!StringUtils.equals(courseAnswer.getUserId(), userId)) {
            ExceptionCast.cast(CourseCode.YOU_NOT_HAVE_AUTHORITY);
        }
        courseAnswerRepository.deleteById(id);
        return ResponseResult.SUCCESS();
    }

    /**
     * 根据companyId查找课程信息
     * @param companyId
     * @return
     */
    public List<CourseBase> findCourseBaseByCompanyId(String companyId) {
        if (StringUtils.isBlank(companyId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        List<CourseBase> courseBaseList = courseBaseRepository.findByCompanyId(companyId);
        return courseBaseList;
    }
}
