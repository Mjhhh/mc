package com.edu.manage_course.service;

import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.response.CmsPageResult;
import com.edu.framework.domain.course.CourseBase;
import com.edu.framework.domain.course.CourseMarket;
import com.edu.framework.domain.course.CoursePic;
import com.edu.framework.domain.course.Teachplan;
import com.edu.framework.domain.course.ext.CourseInfo;
import com.edu.framework.domain.course.ext.CourseView;
import com.edu.framework.domain.course.ext.TeachplanNode;
import com.edu.framework.domain.course.request.CourseListRequest;
import com.edu.framework.domain.course.response.CourseCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.*;
import com.edu.manage_course.client.CmsPageClient;
import com.edu.manage_course.dao.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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
    CmsPageClient cmsPageClient;

    /**
     * 获取课程根结点，如果没有则添加根结点
     *
     * @param courseId 课程id
     * @return 课程根结点
     */
    private String getTeachplanRoot(String courseId) {
        //校验课程id
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()) {
            return null;
        }
        CourseBase courseBase = optional.get();
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
     * 查询课程营销信息
     *
     * @param courseId 课程ID
     * @return 课程营销信息
     */
    private CourseMarket getCourseMarket(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
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
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        return optional.orElse(null);
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
        //进行分页
        if (courseListRequest == null) {
            courseListRequest = new CourseListRequest();
        }
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
        CourseMarket market = this.getCourseMarket(id);
        if (market != null) {
            market.setCharge(courseMarket.getCharge());
            market.setStartTime(courseMarket.getStartTime());
            market.setEndTime(courseMarket.getEndTime());
            market.setPrice(courseMarket.getPrice());
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
        return courseView;
    }

    /**
     * 课程预览页面
     *
     * @param courseId 课程编号
     * @return 课程预览链接
     */
    public CommonResponseResult preview(String courseId) {
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
        //远程请求cms保存页面信息
        CmsPageResult cmsPageResult = cmsPageClient.save(cmsPage);
        if(!cmsPageResult.isSuccess()){
            return new CommonResponseResult(CommonCode.FAIL,null);
        }
        //页面id
        String pageId = cmsPageResult.getCmsPage().getPageId();
        //页面url
        String pageUrl = previewUrl+pageId;
        return new CommonResponseResult(CommonCode.SUCCESS,pageUrl);
    }
}
