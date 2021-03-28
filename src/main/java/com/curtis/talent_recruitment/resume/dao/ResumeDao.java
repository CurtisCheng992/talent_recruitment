package com.curtis.talent_recruitment.resume.dao;

import com.curtis.talent_recruitment.resume.entity.Resume;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 6:04 PM 3/1/2021
 */
@Repository
public interface ResumeDao {

    /**
     * 查询所有简历信息
     */
    List<Resume> getList();

    /**
     * 根据id查询一个简历信息
     *
     * @param mpParam
     * @return
     */
    Resume getDetail(Map<String, Object> mpParam);

    /**
     * 新增一个简历信息
     *
     * @param mpParam
     * @return
     */
    int add(Map<String, Object> mpParam);

    /**
     * 根据id删除一个简历信息
     *
     * @param mpParam
     * @return
     */
    int delete(Map<String, Object> mpParam);

    /**
     * 根据id更新一个简历信息
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
     * 根据用户id查询一个简历信息
     *
     * @param mpParam
     * @return
     */
    Resume getByUserID(Map<String, Object> mpParam);
}
