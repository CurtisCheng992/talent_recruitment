package com.curtis.talent_recruitment.resume.service;

import com.curtis.talent_recruitment.entity.request.resume.AddResume;
import com.curtis.talent_recruitment.entity.request.resume.UpdateResume;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

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

}
