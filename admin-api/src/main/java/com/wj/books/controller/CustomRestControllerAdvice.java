package com.wj.books.controller;

import com.wj.books.commons.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常处理
 *
 * 该类用于全局处理控制器层的异常，旨在提供统一的异常处理机制
 * 以确保系统稳定性，并向客户端返回友好的错误信息
 *
 * @author wujun
 * @date 2025-04-19
 *
 */
@RestControllerAdvice
public class CustomRestControllerAdvice {

    /**
     * 处理所有未被捕获的异常
     *
     * 该方法处理全局范围内未被捕获的异常，将其封装为友好的响应结果返回给客户端
     * 主要目的是避免因未知异常导致系统崩溃或返回不友好的错误信息给客户端
     *
     * @param e 异常对象，包含异常的具体信息
     * @return 返回封装了错误信息的CommonResult对象
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult exception(Exception e) {
        CommonResult result = new CommonResult();
        result.setCode(-1);
        result.setMessage(e.getMessage());
        return result;
    }
}
