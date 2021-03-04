package com.curtis.talent_recruitment.school.dao;

import com.curtis.talent_recruitment.school.entity.School;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:49 PM 2/25/2021
 */
@Repository
public interface SchoolDao {

    /**
     * 查询所有学校信息
     * @return
     */
    List<School> getList();

    /**
     * 根据id查询一所学校信息
     * @param mpParam
     * @return
     */
    School getDetail(Map<String, Object> mpParam);
}
