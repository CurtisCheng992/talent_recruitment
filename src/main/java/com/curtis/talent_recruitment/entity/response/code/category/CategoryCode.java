package com.curtis.talent_recruitment.entity.response.code.category;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:35 PM 2/25/2021
 */
public enum CategoryCode implements ResponseCode {

    CATEGORY_NOT_FOUND( false, 30001, "查询不到分类！" ),
    INSERT_FAIL( false, 30002, "新增分类失败！"),
    DELETE_FAIL( false, 30003, "删除分类失败！"),
    UPDATE_FAIL( false, 30003, "更新分类失败！" ),
    INVALID_PARAM( false, 30004, "部分参数不能为空！" ),
    PARENT_CATEGORY_NOT_FOUND( false, 30005, "找不到父级分类！" ),
    DELETE_FAIL_DIRECTION_CATEGORY_EXIST( false, 30006, "删除失败，请先删除就业方向为此分类的用户！" ),
    DELETE_FAIL_POSITION_EXIST( false, 30007, "删除失败，请先删除此分类下的职位！" ),
    DELETE_FAIL_CHILD_CATEGORY_EXIST( false, 30008, "删除失败，请先删除此分类下的子分类！" );

    CategoryCode(boolean success, int code, String message) {
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
