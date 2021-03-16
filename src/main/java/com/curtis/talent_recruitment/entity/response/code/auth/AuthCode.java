package com.curtis.talent_recruitment.entity.response.code.auth;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description: 授权模块响应代码
 * @Date: Created in 6:26 PM 3/9/2021
 */
public enum AuthCode implements ResponseCode {

    LOGIN_FAIL_BY_PWD( false, 12001, "用户名或密码错误！" ),

    LOGIN_FAIL_BY_EMAIL( false, 12002, "验证码错误！" ),

    LOGIN_SUCCESS( true, 12003, "登录成功！" ),

    VERIFY_SUCCESS( true, 12004, "校验用户身份成功！" ),

    LOGIN_FAIL_BY_PHONE( false, 12005, "验证码错误！" ),

    INVALID_USER( false, 12006, "该用户已被注销，请联系管理员！" );

    private boolean success;
    private int code;
    private String message;

    AuthCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return this.success;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }


}
