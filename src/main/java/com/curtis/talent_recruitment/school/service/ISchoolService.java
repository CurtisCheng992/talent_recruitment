package com.curtis.talent_recruitment.school.service;

import com.curtis.talent_recruitment.entity.response.QueryResponse;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:50 PM 2/25/2021
 */
public interface ISchoolService {

    /**
     * 查询所有学校信息
     * @return
     */
    QueryResponse getList();

    /**
     * 根据id查询一所学校信息
     * @param id
     * @return
     */
    QueryResponse getDetail(String id);
}
