package com.cqyc.shixun.comm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author:
 * @Date:
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YcException extends RuntimeException{
    private ExceptionEnums exceptionEnums;
}
