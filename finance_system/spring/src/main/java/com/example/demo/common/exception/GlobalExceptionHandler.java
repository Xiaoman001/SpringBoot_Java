package com.example.demo.common.exception;

import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description 定义全局异常传参类
 * @Author guanguan
 * @Date 2021/7/16 14:12
 * @Version 1.0
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 校验
     * @param
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WebResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        logger.error(e.getMessage(), e);
        return BaseRest.buildResponse(HttpStatus.FROM,e.getBindingResult().getFieldError().getDefaultMessage());
    }

}
