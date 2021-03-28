package com.curtis.talent_recruitment.company.service;

import com.curtis.talent_recruitment.entity.request.company.AddCompany;
import com.curtis.talent_recruitment.entity.request.company.UpdateCompany;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:45 PM 3/1/2021
 */
public interface ICompanyService {

    /**
     * 查询所有公司信息
     * @return
     */
    QueryResponse getList();

    /**
     * 根据id查询所有分类
     * @param id
     * @return
     */
    QueryResponse getDetail(String id);

    /**
     * 添加一个公司信息
     * @param addCompany
     * @return
     */
    CommonResponse add(AddCompany addCompany);

    /**
     * 删除一个公司信息
     * @param id
     * @return
     */
    CommonResponse delete(String id);

    /**
     * 更新一个公司信息
     * @param id
     * @param updateCompany
     * @return
     */
    CommonResponse update(String id, UpdateCompany updateCompany);

    /**
     * 可限制数量查询热门企业
     *
     * @param iLimit
     * @return
     */
    QueryResponse getHot(Integer iLimit);

    /**
     * 根据HRID查询公司信息
     *
     * @param sHRID
     * @return
     */
    QueryResponse getDetailByHRID(String sHRID);
}
