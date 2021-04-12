package com.curtis.talent_recruitment.resource.dao;

import com.curtis.talent_recruitment.resource.entity.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 12:45 PM 3/27/2021
 */
@Repository
public interface ResourceDao {

    /**
     * 新增资源
     *
     * @param mpParam
     * @return
     */
    int add(Map<String, Object> mpParam);

    /**
     * 根据id查询一个文件资源
     *
     * @param mpParam
     * @return
     */
    Resource getDetail(Map<String, Object> mpParam);

    /**
     * 根据id删除一个文件资源
     *
     * @param mpParam
     * @return
     */
    int delete(Map<String, Object> mpParam);

    /**
     * 根据文件存储路径删除文件
     *
     * @param mpParam
     * @return
     */
    int deleteByLocation(Map<String, Object> mpParam);

    /**
     * 查询所有资源文件
     *
     * @return
     */
    List<Resource> getList(Map<String, Object> mpParam);
}
