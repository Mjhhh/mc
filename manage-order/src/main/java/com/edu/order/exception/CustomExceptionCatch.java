package com.edu.order.exception;

import com.edu.framework.exception.ExceptionCatch;
import com.edu.framework.model.response.CommonCode;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CustomExceptionCatch extends ExceptionCatch {
    static {
        //除了CustomException以外的异常类型及对应的错误代码在这里定义,，如果不定义则统一返回固定的错误信息
        builder.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
        builder.put(SpelEvaluationException.class, CommonCode.UNAUTHORISE);
    }
}
