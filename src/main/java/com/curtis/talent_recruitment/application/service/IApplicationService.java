package com.curtis.talent_recruitment.application.service;

import com.curtis.talent_recruitment.entity.request.application.AddApplication;
import com.curtis.talent_recruitment.entity.request.application.UpdateApplication;
import com.curtis.talent_recruitment.entity.request.resume.AddResume;
import com.curtis.talent_recruitment.entity.request.resume.UpdateResume;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:09 PM 3/1/2021
 */
public interface IApplicationService {

    /**
     * 查询所有简历信息
     *
     * @return
     */
    QueryResponse getList(String sUserID, String sHRID);

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
     * @param addApplication
     * @return
     */
    CommonResponse add(AddApplication addApplication);

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
     * @param updateApplication
     * @return
     */
    CommonResponse update(String id, UpdateApplication updateApplication);

    /**
     * 根据用户id查询一个申请信息
     *
     * @param sUserID
     * @return
     */
    QueryResponse getListByUserID(String sUserID);

    /**
     * 根据用户id查询申请信息记录条数
     *
     * @param sUserID
     * @return
     */
    QueryResponse getCountByUserID(String sUserID);

    /**
     * 判断该用户是否申请过此职位
     *
     * @param sPositionID
     * @param sUserID
     * @return
     */
    QueryResponse getCount(String sPositionID, String sUserID);

    /**
     * 根据条件删除一条申请
     *
     * @param sPositionID
     * @param sUserID
     * @return
     */
    CommonResponse deleteByCondition(String sPositionID, String sUserID);

    /**
     * HR根据申请id完成审核
     *
     * @param id
     * @return
     */
    CommonResponse verify(String sHRID, String id);

    /**
     * 根据HRID统计发布的职位数量
     *
     * @param sHRID
     * @return
     */
    QueryResponse getCountByHR(String sHRID);

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
