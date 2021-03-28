package com.curtis.talent_recruitment.position.service;

import com.curtis.talent_recruitment.entity.request.position.AddPosition;
import com.curtis.talent_recruitment.entity.request.position.UpdatePosition;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:21 PM 3/1/2021
 */
public interface IPositionService {

    /**
     * 查询所有职位信息
     *
     * @return
     */
    QueryResponse getList(String sHRID);

    /**
     * 根据id查询所有分类
     *
     * @param id
     * @return
     */
    QueryResponse getDetail(String id);

    /**
     * 添加一个职位信息
     *
     * @param addPosition
     * @return
     */
    CommonResponse add(AddPosition addPosition);

    /**
     * 删除一个职位信息
     *
     * @param id
     * @return
     */
    CommonResponse delete(String id);

    /**
     * 更新一个职位信息
     *
     * @param id
     * @param updatePosition
     * @return
     */
    CommonResponse update(String id, UpdatePosition updatePosition);

    /**
     * 查询热门职位
     *
     * @param iLimit 查询个数
     * @return
     */
    QueryResponse getHot(Integer iLimit);

    /**
     * 查询最新职位
     *
     * @param iLimit
     * @return
     */
    QueryResponse getNew(Integer iLimit);

    /**
     * 搜索职位
     *
     * @param sPositionName
     * @return
     */
    QueryResponse getSearch(String sPositionName);

    /**
     * 根据HRID统计发布的职位数量
     *
     * @param sHRID
     * @return
     */
    QueryResponse getCount(String sHRID);
}
