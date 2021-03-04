package com.curtis.talent_recruitment.department.service;

import com.curtis.talent_recruitment.entity.request.department.AddDepartment;
import com.curtis.talent_recruitment.entity.request.department.UpdateDepartment;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:34 PM 3/1/2021
 */
public interface IDepartmentService {

    /**
     * 查询所有部门信息
     *
     * @return
     */
    QueryResponse getList();

    /**
     * 根据id查询所有分类
     *
     * @param id
     * @return
     */
    QueryResponse getDetail(String id);

    /**
     * 添加一个部门信息
     *
     * @param addDepartment
     * @return
     */
    CommonResponse add(AddDepartment addDepartment);

    /**
     * 删除一个部门信息
     *
     * @param id
     * @return
     */
    CommonResponse delete(String id);

    /**
     * 更新一个部门信息
     *
     * @param id
     * @param updateDepartment
     * @return
     */
    CommonResponse update(String id, UpdateDepartment updateDepartment);

}
