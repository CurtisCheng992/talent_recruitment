package com.curtis.talent_recruitment.utils.exception;

import com.curtis.talent_recruitment.entity.exception.CustomException;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.ResponseCode;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Curtis
 * @version 1.0.0
 * @description 异常捕获工具类
 * @date 2021/2/23 16:33
 */
@ControllerAdvice
public class ExceptionCatchUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatchUtils.class);

    // 使用 EXCEPTIONS 存放异常类型和错误代码的映射，ImmutableMap 的特点是一旦创建不可改变，并且线程安全
    private static ImmutableMap<Class<? extends Throwable>, ResponseCode> EXCEPTIONS;

    // 使用 builder 来构建一个异常类型和错误代码的异常
    private static ImmutableMap.Builder<Class<? extends Throwable>, ResponseCode> builder = ImmutableMap.builder();

    static { // 在这里加入一些基础的异常类型判断
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
        builder.put(MissingRequestCookieException.class, CommonCode.UNAUTHORIZED);
    }

    // 捕获自定义异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public CommonResponse catchCustomException(CustomException customException) {
        LOGGER.error("捕获自定义异常：{}\r\n 自定义异常：{}", customException.getMessage(), customException);
        return new CommonResponse(customException.getResponseCode());
    }

    // 捕获不可预知异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResponse catchException(Exception exception) {
        LOGGER.error("捕获不可预知异常：{}\r\n 不可预知异常：{}", exception.getMessage(), exception);
        if (EXCEPTIONS == null) EXCEPTIONS = builder.build();
        final ResponseCode responseCode = EXCEPTIONS.get(exception.getClass());
        final CommonResponse commonResponse;
        if (responseCode != null) {
            commonResponse = new CommonResponse(responseCode);
        } else {
            commonResponse = new CommonResponse(CommonCode.SERVER_ERROR);
        }
        return commonResponse;
    }
}
