package com.curtis.talent_recruitment.department.dao;

import com.curtis.talent_recruitment.department.entity.UserDepartment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 10:08 AM 3/2/2021
 */
@Repository
public interface UserDepartmentDao {

    /**
     * 查询所有用户-部门信息
     */
    List<UserDepartment> getList();

    /**
     * 根据id查询一个用户-部门信息
     *
     * @param mpParam
     * @return
     */
    UserDepartment getDetail(Map<String, Object> mpParam);

    /**
     * 新增一个用户-部门信息
     *
     * @param mpParam
     * @return
     */
    int add(Map<String, Object> mpParam);

    /**
     * 根据id删除一个用户-部门信息
     *
     * @param mpParam
     * @return
     */
    int delete(Map<String, Object> mpParam);

    /**
     * 根据id更新一个用户-部门信息
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
