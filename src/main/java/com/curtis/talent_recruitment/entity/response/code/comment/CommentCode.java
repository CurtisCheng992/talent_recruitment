package com.curtis.talent_recruitment.entity.response.code.comment;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 8:12 PM 3/1/2021
 */
public enum CommentCode implements ResponseCode {

    COMMENT_NOT_FOUND ( false, 90001, "查询不到评论信息！" ),
    INSERT_FAIL( false, 90002, "新增评论信息失败！" ),
    DELETE_FAIL( false, 90003, "删除评论信息失败！" ),
    UPDATE_FAIL( false, 90004, "更新评论信息失败！" ),
    INVALID_PARAM( false, 90005, "部分字段不能为空！" ),
    USER_NOT_FOUND( false, 90006, "该用户不存在！" ),
    POSITION_NOT_FOUND( false, 90007, "该职位不存在！" ),
    PARENT_COMMENT_NOT_FOUND( false, 90008, "该父评论不存在！" ),
    DELETE_FAIL_CHILD_COMMENT_EXIST( false, 90009, "删除失败，请先删除该评论下的子评论！" );

    CommentCode(boolean success, int code, String message) {
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
