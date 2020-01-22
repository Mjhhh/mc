package com.edu.manage_course.service;

import com.edu.framework.domain.course.CourseBase;
import com.edu.framework.domain.course.Teachplan;
import com.edu.framework.domain.course.ext.TeachplanNode;
import com.edu.framework.domain.course.response.CourseCommonResult;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_course.dao.CourseBaseRepository;
import com.edu.manage_course.dao.TeachplanMapper;
import com.edu.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    CourseBaseRepository courseBaseRepository;

    @Autowired
    TeachplanRepository teachplanRepository;

    /**
     * 查询课程计划
     *
     * @param courseId 课程id
     * @return 课程计划列表
     */
    public CourseCommonResult findTeachplanList(String courseId) {
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return new CourseCommonResult(CommonCode.SUCCESS, teachplanNode);
    }

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
     * 添加课程计划
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
}
