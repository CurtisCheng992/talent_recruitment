package com.curtis.talent_recruitment.company.dao;

import com.curtis.talent_recruitment.company.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:48 PM 3/1/2021
 */
@Repository
public interface CompanyDao {

    /**
     * 查询所有公司信息
     */
    List<Company> getList();

    /**
     * 根据id查询一个公司信息
     * @param mpParam
     * @return
     */
    Company getDetail(Map<String, Object> mpParam);

    /**
     * 新增一个公司信息
     * @param mpParam
     * @return
     */
    int add(Map<String, Object> mpParam);

    /**
     * 根据id删除一个公司信息
     * @param mpParam
     * @return
     */
    int delete(Map<String, Object> mpParam);

    /**
     * 根据id更新一个公司信息
     * @param mpParam
     * @return
     */
    int update(Map<String, Object> mpParam);

}
