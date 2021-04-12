package com.curtis.talent_recruitment.department.dao;

import com.curtis.talent_recruitment.department.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:36 PM 3/1/2021
 */
@Repository
public interface DepartmentDao {

    /**
     * 查询所有部门信息
     */
    List<Department> getList(Map<String, Object> mpParam);

    /**
     * 根据id查询一个部门信息
     *
     * @param mpParam
     * @return
     */
    Department getDetail(Map<String, Object> mpParam);

    /**
     * 新增一个部门信息
     *
     * @param mpParam
     * @return
     */
    int add(Map<String, Object> mpParam);

    /**
     * 根据id删除一个部门信息
     *
     * @param mpParam
     * @return
     */
    int delete(Map<String, Object> mpParam);

    /**
     * 根据id更新一个部门信息
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
