package com.curtis.talent_recruitment.entity.response.code.user;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:47 PM 2/23/2021
 */
public enum UserCode implements ResponseCode {

    USER_NOT_FOUND( false, 10000, "查询不到用户！" ),
    EMAIL_NOT_REGISTERED( false, 10001, "该邮箱尚未注册，请先进行注册！" ),
    INSERT_FAIL( false, 10002, "新增用户失败！" ),
    USERNAME_ALREADY_EXIST( false, 10003, "用户名已存在！"),
    DELETE_FAIL( false, 10004, "删除用户失败！"),
    UPDATE_FAIL( false, 10005, "更新用户失败！"),
    INVALID_PARAM( false, 10006, "部分参数不能为空！" ),
    SCHOOL_NOT_FOUND( false, 10007, "找不到学校！" ),
    CATEGORY_NOT_FOUND( false, 10008, "找不到分类！" ),
    DELETE_FAIL_RESUME_EXIST( false, 10009, "删除失败，请先删除该用户下的简历！" ),
    DELETE_FAIL_APPLICATION_EXIST( false, 10010, "删除失败，请先删除该用户下的申请！" ),
    DELETE_FAIL_COLLECTION_EXIST( false, 10011, "删除失败，请先删除该用户下的收藏！" ),
    DELETE_FAIL_COMMENT_EXIST( false, 10012, "删除失败，请先删除该用户下的评论！" ),
    DELETE_FAIL_USER_DEPARTMENT_EXIST( false, 10013, "删除失败，请先删除该用户下的用户-部门！" );

    private boolean success;
    private int code;
    private String message;

    UserCode(boolean success, int code, String message) {
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
