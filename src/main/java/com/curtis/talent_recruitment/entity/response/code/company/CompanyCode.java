package com.curtis.talent_recruitment.entity.response.code.company;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:01 PM 3/1/2021
 */
public enum  CompanyCode implements ResponseCode {

    COMPANY_NOT_FOUND( false, 40001, "查询不到公司！" ),
    INSERT_FAIL( false, 40002, "新增公司信息失败！" ),
    DELETE_FAIL( false, 40003, "删除公司信息失败！" ),
    UPDATE_FAIL( false, 40004, "更新公司信息失败！" ),
    INVALID_PARAM( false, 40005, "sCompanyName字段不能为空！" ),
    DELETE_FAIL_DEPARTMENT_EXIST( false, 40006, "删除失败，请先删除该公司下的部门！" );

    CompanyCode(boolean success, int code, String message) {
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
