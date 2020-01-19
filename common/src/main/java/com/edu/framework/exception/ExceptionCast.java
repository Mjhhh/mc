package com.edu.framework.exception;

import com.edu.framework.model.response.ResultCode;

/**
 * @author mjh
 */
public class ExceptionCast {
    /**
     * 使用此静态方法抛出自定义异常
     * @param resultCode 抛出信息
     */
    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }
}

