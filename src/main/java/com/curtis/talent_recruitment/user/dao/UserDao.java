package com.curtis.talent_recruitment.user.dao;

import com.curtis.talent_recruitment.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:11 PM 2/23/2021
 */
@Repository("UserDao")
public interface UserDao {

    /**
     * 查询所有用户
     * @return
     */
    List<User> getList();

    /**
     * 根据id查询单个用户
     * @param mpParam
     * @return
     */
    User getDetail(Map<String, Object> mpParam);

    /**
     * 创建用户
     * @param mpParam
     */
    int add(Map<String, Object> mpParam);

    /**
     * 根据id删除一个用户
     * @param mpParam
     * @return
     */
    int delete(Map<String, Object> mpParam);

    /**
     * 更新用户信息
     * @param mpParam
     * @return
     */
    int update(Map<String, Object> mpParam);

    /**
     * 根据条件查询是否存在此用户
     * @param mpParam
     * @return
     */
    int getCount(Map<String, Object> mpParam);

}
