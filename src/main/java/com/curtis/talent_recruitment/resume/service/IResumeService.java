package com.curtis.talent_recruitment.resume.service;

import com.curtis.talent_recruitment.entity.request.resume.AddResume;
import com.curtis.talent_recruitment.entity.request.resume.UpdateResume;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 6:02 PM 3/1/2021
 */
public interface IResumeService {

    /**
     * 查询所有简历信息
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
     * 添加一个简历信息
     *
     * @param addResume
     * @return
     */
    CommonResponse add(AddResume addResume);

    /**
     * 删除一个简历信息
     *
     * @param id
     * @return
     */
    CommonResponse delete(String id);

    /**
     * 更新一个简历信息
     *
     * @param id
     * @param updateResume
     * @return
     */
    CommonResponse update(String id, UpdateResume updateResume);

    /**
     * 根据用户id查询一个简历信息
     *
     * @param sUserID
     * @return
     */
    QueryResponse getByUserID(String sUserID);

    /**
     * 更新图片
     *
     * @param id
     * @param sPicture
     * @return
     */
    CommonResponse updatePicture(String id, String sPicture);

    /**
     * 根据条件分页查询
     *
     * @param lCurrentPage
     * @param lPageSize
     * @param mpParam
     * @return
     */
    QueryResponse getByPage(Long lCurrentPage, Long lPageSize, Map<String, Object> mpParam);

    /**
     * 根据id查询简历是否存在
     *
     * @param sUserID
     * @return
     */
    QueryResponse getCount(String sUserID);
}
