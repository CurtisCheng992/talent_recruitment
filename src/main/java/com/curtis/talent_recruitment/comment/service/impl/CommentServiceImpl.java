package com.curtis.talent_recruitment.comment.service.impl;

import com.curtis.talent_recruitment.comment.dao.CommentDao;
import com.curtis.talent_recruitment.comment.entity.Comment;
import com.curtis.talent_recruitment.comment.service.ICommentService;
import com.curtis.talent_recruitment.entity.request.comment.AddComment;
import com.curtis.talent_recruitment.entity.request.comment.UpdateComment;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.comment.CommentCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.position.dao.PositionDao;
import com.curtis.talent_recruitment.position.entity.Position;
import com.curtis.talent_recruitment.user.dao.UserDao;
import com.curtis.talent_recruitment.user.entity.User;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import com.hs.commons.utils.ConvertUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 8:19 PM 3/1/2021
 */
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PositionDao positionDao;

    /**
     * 查询所有评论信息
     * @return
     */
    @Override
    public QueryResponse getList() {
        List<Comment> arrComment = commentDao.getList();
        return new QueryResponse(CommonCode.SUCCESS,new QueryResult(arrComment, arrComment.size()));
    }

    /**
     * 根据id查询一个评论信息
     * @param id
     * @return
     */
    @Override
    public QueryResponse getDetail(String id) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (!StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //根据id查询一个评论信息
        mpParam.put("id",id);
        Comment comment = commentDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(comment)){
            return new QueryResponse(CommentCode.COMMENT_NOT_FOUND,null);
        }
        List<Comment> arrComment = new ArrayList<>();
        arrComment.add(comment);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrComment, arrComment.size()));
    }

    /**
     * 新增一个评论信息
     * @param addComment
     * @return
     */
    @Override
    public CommonResponse add(AddComment addComment) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(addComment)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sContent,sUserID,sPositionID不能为空
        if (!StringUtils.isNoneBlank(addComment.getSContent()) ||
        !StringUtils.isNoneBlank(addComment.getSUserID()) ||
        !StringUtils.isNoneBlank(addComment.getSPositionID())){
            ExceptionThrowUtils.cast(CommentCode.INVALID_PARAM);
        }
        //根据sUserID判断该用户是否存在
        mpParam.put("id",addComment.getSUserID());
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(CommentCode.USER_NOT_FOUND);
        }
        mpParam.clear();
        //根据sPositionID判断该职位是否存在
        mpParam.put("id",addComment.getSPositionID());
        Position position = positionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(position)){
            return new CommonResponse(CommentCode.POSITION_NOT_FOUND);
        }
        mpParam.clear();
        //如果存在sParentID,判断该父评论是否存在
        if (StringUtils.isNoneBlank(addComment.getSParentID())){
            mpParam.put("id",addComment.getSParentID());
            Comment parentComment = commentDao.getDetail(mpParam);
            if (ObjectUtils.isEmpty(parentComment)){
                return new CommonResponse(CommentCode.PARENT_COMMENT_NOT_FOUND);
            }
            mpParam.clear();
        }
        //新增一个评论信息
        mpParam = ConvertUtils.objectToMap(addComment);
        mpParam.put("id", com.hs.commons.utils.StringUtils.getUUIDString());
        mpParam.put("dCreateTime", new Date());
        mpParam.put("dUpdateTime", new Date());
        if (addComment.getSParentID() == ""){
            mpParam.put("sParentID",null);
        }
        int iResult = commentDao.add(mpParam);
        if (iResult <= 0){
            return new CommonResponse(CommentCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除一个评论信息
     * @param id
     * @return
     */
    @Override
    public CommonResponse delete(String id) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (!StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //判断该评论下是否存在子评论
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("sParentID",id);
        int iCount = commentDao.getCount(mpParam);
        if (iCount >= 1){
            return new CommonResponse(CommentCode.DELETE_FAIL_CHILD_COMMENT_EXIST);
        }
        //根据id查询该评论是否存在
        mpParam.put("id",id);
        Comment comment = commentDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(comment)){
            return new CommonResponse(CommentCode.COMMENT_NOT_FOUND);
        }
        int iResult = commentDao.delete(mpParam);
        if (iResult <=0 ){
            return new CommonResponse(CommentCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新一个评论信息
     * @param id
     * @param updateComment
     * @return
     */
    @Override
    public CommonResponse update(String id, UpdateComment updateComment) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(updateComment) && !StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sContent,sUserID,sPositionID不能为空
        if (!StringUtils.isNoneBlank(updateComment.getSContent()) ||
                !StringUtils.isNoneBlank(updateComment.getSParentID()) ||
                !StringUtils.isNoneBlank(updateComment.getSPositionID())){
            ExceptionThrowUtils.cast(CommentCode.INVALID_PARAM);
        }
        //根据sUserID判断该用户是否存在
        mpParam.put("id",updateComment.getSUserID());
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(CommentCode.USER_NOT_FOUND);
        }
        mpParam.clear();
        //根据sPositionID判断该职位是否存在
        mpParam.put("id",updateComment.getSPositionID());
        Position position = positionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(position)){
            return new CommonResponse(CommentCode.POSITION_NOT_FOUND);
        }
        mpParam.clear();
        //如果存在sParentID,判断该父评论是否存在
        if (StringUtils.isNoneBlank(updateComment.getSParentID())){
            mpParam.put("id",updateComment.getSParentID());
            Comment parentComment = commentDao.getDetail(mpParam);
            if (ObjectUtils.isEmpty(parentComment)){
                return new CommonResponse(CommentCode.PARENT_COMMENT_NOT_FOUND);
            }
            mpParam.clear();
        }
        //根据id判断该评论是否存在
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",id);
        Comment comment = commentDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(comment)){
            return new CommonResponse(CommentCode.COMMENT_NOT_FOUND);
        }
        mpParam = ConvertUtils.objectToMap(updateComment);
        mpParam.put("id",id);
        mpParam.put("dUpdateTime",new Date());
        int iResult = commentDao.update(mpParam);
        if (iResult <= 0){
            return new CommonResponse(CommentCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }
}
