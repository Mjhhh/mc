package com.edu.framework.exception;

import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.ResultCode;
import org.springframework.expression.spel.SpelEvaluationException;

/**
 * @author mjh
 * 自定义异常类
 */
public class CustomException extends RuntimeException {
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        //异常信息为错误代码+异常信息
        super("错误代码：" + resultCode.code() + "错误信息：" + resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }
}
