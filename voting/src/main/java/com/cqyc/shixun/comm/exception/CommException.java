package com.cqyc.shixun.comm.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author:
 * @Date:
 */
@ControllerAdvice
public class CommException {

    @ExceptionHandler(YcException.class)
    @ResponseBody
    public ResponseEntity<String> handleException(YcException e){
        ExceptionEnums em = e.getExceptionEnums();
        System.out.println("--------获取自定义异常YcException--------"+em.getMsg());
        return ResponseEntity.status(em.getCode()).body(em.getMsg());
    }
}
