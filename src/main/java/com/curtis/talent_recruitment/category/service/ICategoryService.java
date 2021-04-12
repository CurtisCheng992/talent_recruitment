package com.curtis.talent_recruitment.category.service;

import com.curtis.talent_recruitment.entity.request.school.AddCategory;
import com.curtis.talent_recruitment.entity.request.school.UpdateCategory;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:17 PM 2/25/2021
 */
public interface ICategoryService {

    /**
     * 查询所有分类
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
     * 添加一个分类
     * @param addCategory
     * @return
     */
    CommonResponse add(AddCategory addCategory);

    /**
     * 删除一个分类
     * @param id
     * @return
     */
    CommonResponse delete(String id);

    /**
     * 更新一个分类
     * @param id
     * @param updateCategory
     * @return
     */
    CommonResponse update(String id, UpdateCategory updateCategory);

    /**
     * 根据分类名称查询分类信息
     *
     * @param sCategoryName
     * @return
     */
    QueryResponse getByName(String sCategoryName);

    /**
     * 根据条件分页查询
     *
     * @param lCurrentPage
     * @param lPageSize
     * @param mpParam
     * @return
     */
    QueryResponse getByPage(Long lCurrentPage, Long lPageSize, Map<String, Object> mpParam);
}
