package com.curtis.talent_recruitment.entity.response.code.resume;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 6:00 PM 3/1/2021
 */
public enum  ResumeCode implements ResponseCode {

    RESUME_NOT_FOUND ( false, 70001, "查询不到简历信息！" ),
    INSERT_FAIL( false, 70002, "新增简历信息失败！" ),
    DELETE_FAIL( false, 70003, "删除简历信息失败！" ),
    UPDATE_FAIL( false, 70004, "更新简历信息失败！" ),
    USERID_CANT_BE_NULL( false, 70005, "用户id字段不能为空！" ),
    USER_NOT_FOUND( false, 70006, "找不到用户！" ),
    DELETE_FAIL_APPLICATION_EXIST( false, 70007, "删除失败，请先删除该简历下的申请！" );

    ResumeCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    private boolean success;
    private int code;
    private String message;

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
