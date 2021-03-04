package com.curtis.talent_recruitment.utils.exception;

import com.curtis.talent_recruitment.entity.exception.CustomException;
import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @author Curtis
 * @version 1.0.0
 * @description 自定义异常抛出工具类
 * @date 2021/2/23 16:30
 */
public class ExceptionThrowUtils {

    // 抛出自定义异常
    public static void cast(ResponseCode responseCode) {
        throw new CustomException(responseCode);
    }

}
