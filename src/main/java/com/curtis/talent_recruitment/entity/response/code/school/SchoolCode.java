package com.curtis.talent_recruitment.entity.response.code.school;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:06 PM 2/25/2021
 */
public enum SchoolCode implements ResponseCode {

    SCHOOL_NOT_FOUND( false, 11001, "查询不到学校！");

    private boolean success;
    private int code;
    private String message;

    SchoolCode(boolean success, int code, String message){
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
