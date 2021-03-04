package com.curtis.talent_recruitment.comment.service;

import com.curtis.talent_recruitment.entity.request.comment.AddComment;
import com.curtis.talent_recruitment.entity.request.comment.UpdateComment;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 8:17 PM 3/1/2021
 */
public interface ICommentService {

    /**
     * 查询所有评论信息
     *
     * @return
     */
    QueryResponse getList();

    /**
     * 根据id查询所有分类
     *
     * @param id
     * @return
     */
    QueryResponse getDetail(String id);

    /**
     * 添加一个评论信息
     *
     * @param addComment
     * @return
     */
    CommonResponse add(AddComment addComment);

    /**
     * 删除一个评论信息
     *
     * @param id
     * @return
     */
    CommonResponse delete(String id);

    /**
     * 更新一个评论信息
     *
     * @param id
     * @param updateComment
     * @return
     */
    CommonResponse update(String id, UpdateComment updateComment);

}
