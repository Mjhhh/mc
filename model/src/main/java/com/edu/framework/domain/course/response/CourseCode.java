package com.edu.framework.domain.course.response;

import com.edu.framework.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;


/**
 * Created by Mjh on 2019-11-11
 */
@ToString
public enum CourseCode implements ResultCode {

    COURSE_DENIED_DELETE(false,31001,"删除课程失败，只允许删除本机构的课程！"),
    COURSE_PUBLISH_PERVIEWISNULL(false,31002,"还没有进行课程预览！"),
    COURSE_PUBLISH_CDETAILERROR(false,31003,"创建课程详情页面出错！"),
    COURSE_PUBLISH_COURSEIDISNULL(false,31004,"课程ID为空！"),
    COURSE_PUBLISH_VIEWERROR(false,31005,"发布课程视图出错！"),
    COURSE_PUBLISH_IS_NULL(false,31006,"找不到相关的课程"),
    COURSE_MEDIS_URLISNULL(false,31101,"选择的媒资文件访问地址为空！"),
    COURSE_MEDIS_NAMEISNULL(false,31102,"选择的媒资文件名称为空！"),
    COURSE_COMPANY_IS_NULL(false,31201,"公司ID为空"),
    COURSE_PICTURE_IS_NULL(false,31202,"课程图片为空"),
    COURSE_MARKET_IS_NULL(false,31203,"课程营销计划为空"),
    COURSE_PUB_IS_NULL(false,31204,"课程发布表为空"),
    COURSE_MEDIA_TEACHPLAN_GRADE_ERROR(false, 31205, "当前课程节点不允许操作"),
    COURSE_CATEGORY_IS_NULL(false, 31206, "课程分类为空"),
    COURSE_EVALUATE_IS_NULL(false, 31206, "课程评论为空"),
    COURSE_PARAMS_IS_NULL(false, 31207, "重要参数为空"),
    YOU_NOT_HAVE_AUTHORITY(false, 31209, "你没有权限进行操作"),
    COURSE_PRICE_IS_NULL(false, 31208, "价格不能为空"),
    COURSE_EVALUATE_ALREADY_EXISTS(false, 31209, "您已经评论过了");

    /**
     * 操作是否成功
     */
    boolean success;

    /**
     * 操作代码
     */
    int code;

    /**
     * 提示信息
     */
    String message;

    CourseCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
