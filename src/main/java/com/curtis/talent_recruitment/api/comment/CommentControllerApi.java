package com.curtis.talent_recruitment.api.comment;

import com.curtis.talent_recruitment.entity.request.comment.AddComment;
import com.curtis.talent_recruitment.entity.request.comment.UpdateComment;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 8:15 PM 3/1/2021
 */
@Api(value = "评论模块管理接口", description = "评论模块管理接口，提供评论的增、删、改、查")
public interface CommentControllerApi {

    @ApiOperation("查询所有评论信息")
    QueryResponse getList();

    @ApiOperation("查询一个评论信息")
    @ApiImplicitParam(name = "id", value = "评论主键id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getDetail(String id);

    @ApiOperation("新增一个评论")
    CommonResponse add(AddComment addComment);

    @ApiOperation("删除一个评论")
    @ApiImplicitParam(name = "id", value = "评论主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse delete(String id);

    @ApiOperation("修改一个评论")
    @ApiImplicitParam(name = "id", value = "评论主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse update(String id, UpdateComment updateComment);

}
