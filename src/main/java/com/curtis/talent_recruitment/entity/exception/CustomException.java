package com.curtis.talent_recruitment.entity.exception;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @author Curtis
 * @version 1.0.0
 * @description 自定义异常类
 * @date 2021/2/23 16:27
 */
public class CustomException extends RuntimeException {

    private ResponseCode responseCode;

    public CustomException(ResponseCode responseCode) {
        super("错误代码：" + responseCode.code() + ", 错误信息：" + responseCode.message());
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
