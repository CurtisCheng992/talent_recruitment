package com.curtis.talent_recruitment.comment.dao;

import com.curtis.talent_recruitment.comment.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 8:16 PM 3/1/2021
 */
@Repository
public interface CommentDao {

    /**
     * 查询所有评论信息
     */
    List<Comment> getList();

    /**
     * 根据id查询一个评论信息
     *
     * @param mpParam
     * @return
     */
    Comment getDetail(Map<String, Object> mpParam);

    /**
     * 新增一个评论信息
     *
     * @param mpParam
     * @return
     */
    int add(Map<String, Object> mpParam);

    /**
     * 根据id删除一个评论信息
     *
     * @param mpParam
     * @return
     */
    int delete(Map<String, Object> mpParam);

    /**
     * 根据id更新一个评论信息
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
