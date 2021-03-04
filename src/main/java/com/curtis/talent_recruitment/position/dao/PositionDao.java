package com.curtis.talent_recruitment.position.dao;

import com.curtis.talent_recruitment.position.entity.Position;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:23 PM 3/1/2021
 */
@Repository
public interface PositionDao {

    /**
     * 查询所有职位信息
     */
    List<Position> getList();

    /**
     * 根据id查询一个职位信息
     *
     * @param mpParam
     * @return
     */
    Position getDetail(Map<String, Object> mpParam);

    /**
     * 新增一个职位信息
     *
     * @param mpParam
     * @return
     */
    int add(Map<String, Object> mpParam);

    /**
     * 根据id删除一个职位信息
     *
     * @param mpParam
     * @return
     */
    int delete(Map<String, Object> mpParam);

    /**
     * 根据id更新一个职位信息
     *
     * @param mpParam
     * @return
     */
    int update(Map<String, Object> mpParam);

    /**
     * 更新查看人数
     *
     * @param mpParam
     * @return
     */
    int updateViews(Map<String, Object> mpParam);

    /**
     * 根据条件查询统计信息
     *
     * @return
     */
    int getCount(Map<String, Object> mpParam);

}
