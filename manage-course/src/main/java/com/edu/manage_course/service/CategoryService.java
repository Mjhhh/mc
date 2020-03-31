package com.edu.manage_course.service;

import com.edu.framework.domain.course.Category;
import com.edu.framework.domain.course.ext.CategoryNode;
import com.edu.framework.domain.course.response.CourseCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_course.dao.CategoryMapper;
import com.edu.manage_course.dao.CategoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * 获取分类信息
     * @param id
     * @return
     */
    private Category getCategory(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CourseCode.COURSE_PARAMS_IS_NULL);
        }
        Optional<Category> optional = categoryRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 查询分类
     * @return 分类树
     */
    public CategoryNode findList() {
        return categoryMapper.selectList();
    }

    /**
     * 根据id查询分类列表
     * @return
     */
    public List<Category> findByIds(List<String> ids) {
        return categoryRepository.findAllById(ids);
    }

    /**
     * 添加分类信息
     * @param category
     * @return
     */
    @Transactional
    public ResponseResult addCourseCategory(Category category) {
        if (category == null
                || StringUtils.isBlank(category.getName())
                || StringUtils.isBlank(category.getLabel())
                || StringUtils.isBlank(category.getIsleaf())) {
            ExceptionCast.cast(CourseCode.COURSE_PARAMS_IS_NULL);
        }
        if (category.getParentid() == null) {
            category.setParentid("1");
        }
        String parentid = category.getParentid();
        List<Category> categoryList = categoryRepository.findByParentid(parentid);
        int size = categoryList.size();
        size ++ ;
        String id = parentid + "-" + size;
        while (true) {
            Category isExist = this.getCategory(id);
            if (isExist == null) {
                break;
            } else {
                size ++ ;
                id = parentid + "-" + size;
            }
        }
        category.setId(id);
        category.setIsshow("1");
        category.setOrderby(1);
        categoryRepository.save(category);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除课程分类信息
     * @param id
     * @return
     */
    @Transactional
    public ResponseResult delCourseCategory(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CourseCode.COURSE_PARAMS_IS_NULL);
        }
        Category category = this.getCategory(id);
        if (category == null) {
            ExceptionCast.cast(CourseCode.COURSE_CATEGORY_IS_NULL);
        }
        //先删除下级分类
        categoryRepository.deleteByParentid(category.getId());
        //再删除本身
        categoryRepository.deleteById(category.getId());
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
