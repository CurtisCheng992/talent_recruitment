package com.curtis.talent_recruitment.entity.response.code.resource;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:34 PM 3/28/2021
 */
public enum ResourceCode implements ResponseCode {

    USER_NOT_FOUND( false, 14001, "找不到用户！" ),
    CONTENT_TYPE_INVALID( false, 14002, "暂不支持该文件类型！" ),
    INSERT_FAIL( false, 14003, "新增文件资源失败！" ),
    DELETE_FAIL( false, 14004, "删除文件资源失败！" );

    private boolean success;
    private int code;
    private String message;

    ResourceCode(boolean success, int code, String message) {
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
