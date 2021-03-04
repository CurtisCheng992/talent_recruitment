package com.curtis.talent_recruitment.entity.response.code.department;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:45 PM 3/1/2021
 */
public enum  DepartmentCode implements ResponseCode {

    DEPARTMENT_NOT_FOUND ( false, 50001, "查询不到部门信息！" ),
    INSERT_FAIL( false, 50002, "新增部门信息失败！" ),
    DELETE_FAIL( false, 50003, "删除部门信息失败！" ),
    UPDATE_FAIL( false, 50004, "更新部门信息失败！" ),
    INVALID_PARAM( false, 50005, "参数sDepartmentName和sCompanyID不能为空！" ),
    COMPANY_NOT_FOUND( false, 50006, "该公司不存在！" ),
    DELETE_FAIL_POSITION_EXIST( false, 50007, "删除失败，请先删除该部门下的职位！" ),
    DELETE_FAIL_USER_DEPARTMENT_EXIST(false, 50008, "删除失败，请先删除用户-部门信息！");

    DepartmentCode(boolean success, int code, String message) {
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
