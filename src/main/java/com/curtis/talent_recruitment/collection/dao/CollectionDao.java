package com.curtis.talent_recruitment.collection.dao;

import com.curtis.talent_recruitment.collection.entity.Collection;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:49 PM 3/1/2021
 */
@Repository
public interface CollectionDao {

    /**
     * 查询所有收藏信息
     */
    List<Collection> getList();

    /**
     * 根据id查询一个收藏信息
     *
     * @param mpParam
     * @return
     */
    Collection getDetail(Map<String, Object> mpParam);

    /**
     * 新增一个收藏信息
     *
     * @param mpParam
     * @return
     */
    int add(Map<String, Object> mpParam);

    /**
     * 根据id删除一个收藏信息
     *
     * @param mpParam
     * @return
     */
    int delete(Map<String, Object> mpParam);

    /**
     * 根据id更新一个收藏信息
     *
     * @param mpParam
     * @return
     */
    int update(Map<String, Object> mpParam);

    /**
     * 根据条件查询统计信息
     *
     * @param mpParam
     * @return
     */
    int getCount(Map<String, Object> mpParam);

    /**
     * 根据用户id查询收藏信息
     *
     * @param mpParam
     * @return
     */
    List<Collection> getListByUserID(Map<String, Object> mpParam);

    /**
     * 根据条件删除一条收藏
     *
     * @param mpParam
     * @return
     */
    int deleteByCondition(Map<String, Object> mpParam);
}
