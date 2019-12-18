package com.edu.framework.exception;

import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 */
//控制器增强
@ControllerAdvice
public class ExceptionCatch {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);
    //捕获 CustomException异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e) {
        LOGGER.error("catch exception : {}\r\nexception: ",e.getMessage());
        ResultCode resultCode = e.getResultCode();
        return new ResponseResult(resultCode);
    }
}
