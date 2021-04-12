package com.curtis.talent_recruitment.collection.service;

import com.curtis.talent_recruitment.entity.request.collection.AddCollection;
import com.curtis.talent_recruitment.entity.request.collection.UpdateCollection;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:47 PM 3/1/2021
 */
public interface ICollectionService {

    /**
     * 查询所有收藏信息
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
     * 添加一个收藏信息
     *
     * @param addCollection
     * @return
     */
    CommonResponse add(AddCollection addCollection);

    /**
     * 删除一个收藏信息
     *
     * @param id
     * @return
     */
    CommonResponse delete(String id);

    /**
     * 更新一个收藏信息
     *
     * @param id
     * @param updateCollection
     * @return
     */
    CommonResponse update(String id, UpdateCollection updateCollection);

    /**
     * 根据用户id查询一条收藏信息
     *
     * @param sUserID
     * @return
     */
    QueryResponse getListByUserID(String sUserID);

    /**
     * 根据用户id查询收藏记录条数
     *
     * @param sUserID
     * @return
     */
    QueryResponse getCountByUserID(String sUserID);

    /**
     * 根据条件删除一条收藏
     *
     * @param sPositionID
     * @param sUserID
     * @return
     */
    CommonResponse deleteByCondition(String sPositionID, String sUserID);

    /**
     * 判断该用户是否收藏过此职位
     *
     * @param sPositionID
     * @param sUserID
     * @return
     */
    QueryResponse getCount(String sPositionID, String sUserID);

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
