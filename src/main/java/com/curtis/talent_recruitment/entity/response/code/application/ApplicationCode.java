package com.curtis.talent_recruitment.entity.response.code.application;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:05 PM 3/1/2021
 */
public enum ApplicationCode implements ResponseCode {

    APPLICATION_NOT_FOUND ( false, 80001, "查询不到申请信息！" ),
    INSERT_FAIL( false, 80002, "新增申请信息失败！" ),
    DELETE_FAIL( false, 80003, "删除申请信息失败！" ),
    UPDATE_FAIL( false, 80004, "更新申请信息失败！" ),
    INVALID_PARAM( false, 80005, "部分参数不能为空！" ),
    RESUME_NOT_FOUND( false, 80006, "找不到该简历！" ),
    POSITION_NOT_FOUND( false, 80007, "找不到该职位！" ),
    HR_NOT_FOUND( false, 80008, "找不到该HR用户！" );

    ApplicationCode(boolean success, int code, String message) {
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
