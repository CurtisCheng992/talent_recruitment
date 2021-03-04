package com.curtis.talent_recruitment.entity.response.code.collection;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:44 PM 3/1/2021
 */
public enum  CollectionCode implements ResponseCode {

    COLLECTION_NOT_FOUND ( false, 80001, "查询不到收藏信息！" ),
    INSERT_FAIL( false, 80002, "新增收藏信息失败！" ),
    DELETE_FAIL( false, 80003, "删除收藏信息失败！" ),
    UPDATE_FAIL( false, 80004, "更新收藏信息失败！" ),
    INVALID_PARAM( false, 80005, "部分参数不能为空！" ),
    USER_NOT_FOUND( false, 80006, "找不到用户信息！" ),
    POSITION_NOT_FOUND( false, 80007, "找不到职位信息！" );

    CollectionCode(boolean success, int code, String message) {
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
