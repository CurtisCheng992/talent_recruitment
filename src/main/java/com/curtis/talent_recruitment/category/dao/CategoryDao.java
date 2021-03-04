package com.curtis.talent_recruitment.category.dao;

import com.curtis.talent_recruitment.category.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:18 PM 2/25/2021
 */
@Repository
public interface CategoryDao {

    /**
     * 查询所有分类信息
     *
     * @return
     */
    List<Category> getList();

    /**
     * 根据id查询一个分类信息
     *
     * @param mpParam
     * @return
     */
    Category getDetail(Map<String, Object> mpParam);

    /**
     * 新增一个分类
     *
     * @param mpParam
     * @return
     */
    int add(Map<String, Object> mpParam);

    /**
     * 根据id删除一个分类
     *
     * @param mpParam
     * @return
     */
    int delete(Map<String, Object> mpParam);

    /**
     * 根据id更新一个分类
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
