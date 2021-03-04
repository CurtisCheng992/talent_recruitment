package com.curtis.talent_recruitment.collection.service;

import com.curtis.talent_recruitment.entity.request.collection.AddCollection;
import com.curtis.talent_recruitment.entity.request.collection.UpdateCollection;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

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

}
