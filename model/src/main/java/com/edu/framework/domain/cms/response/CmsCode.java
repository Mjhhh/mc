package com.edu.framework.domain.cms.response;

import com.edu.framework.model.response.ResultCode;
import lombok.ToString;

/**
 * @author Admin
 */

@ToString
public enum CmsCode implements ResultCode {
    CMS_ADDPAGE_EXISTSNAME(false,24001,"页面名称已存在！"),
    CMS_GENERATEHTML_DATAURLISNULL(false,24002,"从页面信息中找不到获取数据的url！"),
    CMS_GENERATEHTML_DATAISNULL(false,24003,"根据页面的数据url获取不到数据！"),
    CMS_GENERATEHTML_TEMPLATEISNULL(false,24004,"页面模板为空！"),
    CMS_GENERATEHTML_HTMLISNULL(false,24005,"生成的静态html为空！"),
    CMS_GENERATEHTML_SAVEHTMLERROR(false,24005,"保存静态html出错！"),
    CMS_COURSE_PERVIEWISNULL(false,24007,"预览页面为空！"),
    CMS_PAGE_PARAMS_ISNULL(false,24008,"传入的页面信息或者参数缺失！"),
    CMS_PAGE_IS_NOT_EXISTS(false,24009,"页面不存在！"),
    CMS_PAGE_POST_FAIL(false,240010,"发布页面失败！"),
    CMS_SAVE_PAGE_FAIL(false,24011,"保存页面失败！"),
    CMS_SITEID_IS_NOTEXISTS(false,24012,"站点ID不存在！"),
    CMS_SITE_IS_NOTEXISTS(false,24013,"站点不存在！"),
    CMS_CONFIG_IS_NOTEXISTS(false,24014,"页面配置不存在！"),
    CMS_PIC_IS_NOTEXISTS(false,24015,"图片配置不存在！");

    /**
     * 是否成功
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
    CmsCode(boolean success, int code, String message){
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
