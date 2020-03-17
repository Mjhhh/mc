package com.edu.framework.domain.portalview.response;

import com.edu.framework.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;
import lombok.ToString;


/**
 * Created by Mjh on 2019-11-11
 */
@ToString
public enum PortalViewCode implements ResultCode {
    PortalView_ADDVIEWCOURSE_COURSEIDISNULL(false,42001,"课程id为空！"),
    PortalView_PUBLISH_PREVIEWCOURSE_ISNULL(false,42002,"预览课程视图为空！"),
    PortalView_PUBLISH_PREVIEWMEDIA_ISNULL(false,42003,"预览课程媒资视图为空！");

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
    PortalViewCode(boolean success, int code, String message){
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
