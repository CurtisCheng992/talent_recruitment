package com.curtis.talent_recruitment.application.dao;

import com.curtis.talent_recruitment.application.entity.Application;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:13 PM 3/1/2021
 */
@Repository
public interface ApplicationDao {

    /**
     * 查询所有申请信息
     */
    List<Application> getList();

    /**
     * 根据id查询一个申请信息
     *
     * @param mpParam
     * @return
     */
    Application getDetail(Map<String, Object> mpParam);

    /**
     * 新增一个申请信息
     *
     * @param mpParam
     * @return
     */
    int add(Map<String, Object> mpParam);

    /**
     * 根据id删除一个申请信息
     *
     * @param mpParam
     * @return
     */
    int delete(Map<String, Object> mpParam);

    /**
     * 根据id更新一个申请信息
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
}
