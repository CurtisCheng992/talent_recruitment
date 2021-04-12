package com.curtis.talent_recruitment.entity.response.code.position;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:31 PM 3/1/2021
 */
public enum  PositionCode implements ResponseCode {

    POSITION_NOT_FOUND ( false, 60001, "查询不到职位信息！" ),
    INSERT_FAIL( false, 60002, "新增职位信息失败！" ),
    DELETE_FAIL( false, 60003, "删除职位信息失败！" ),
    UPDATE_FAIL( false, 60004, "更新职位信息失败！" ),
    INVALID_PARAM( false, 60005, "部分字段不能为空！" ),
    CATEGORY_NOT_FOUND( false, 60006, "找不到分类！" ),
    DEPARTMENT_NOT_FOUND( false, 60007, "找不到部门！" ),
    QUANTITY_CANT_LESS_THAN_1( false, 60008, "招聘人数不能小于1！" ),
    DELETE_FAIL_COMMENT_EXIST( false, 60009, "删除失败，请先删除该职位下的评论！" ),
    DELETE_FAIL_APPLICATION_EXIST( false, 60010, "删除失败，请先删除该职位下的申请！" ),
    DELETE_FAIL_COLLECTION_EXIST( false, 60011, "删除失败，请先删除该职位下的收藏！" ),
    COMPANY_NOT_FOUND( false, 60012, "找不到公司！" ),
    HR_NOT_FOUND( false, 60013, "找不到HR用户！" ),
    USER_IS_NOT_HR( false, 60014, "该用户不是HR！" ),
    UPDATE_HOT_FAIL( false, 60015, "更新职位热门值失败！" ),
    POSITION_ADD_FAIL_COMPANY_NOT_EXIST( false, 60016, "创建职位失败，请先完善公司信息！" );

    PositionCode(boolean success, int code, String message) {
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
