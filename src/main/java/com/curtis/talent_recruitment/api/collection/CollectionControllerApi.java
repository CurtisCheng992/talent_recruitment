package com.curtis.talent_recruitment.api.collection;

import com.curtis.talent_recruitment.entity.request.collection.AddCollection;
import com.curtis.talent_recruitment.entity.request.collection.UpdateCollection;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:42 PM 3/1/2021
 */
@Api(value = "收藏模块管理接口", description = "收藏模块管理接口，提供收藏的增、删、改、查")
public interface CollectionControllerApi {

    @ApiOperation("查询所有收藏信息")
    QueryResponse getList();

    @ApiOperation("查询一个收藏信息")
    @ApiImplicitParam(name = "id", value = "收藏主键id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getDetail(String id);

    @ApiOperation("新增一个收藏")
    CommonResponse add(AddCollection addCollection);

    @ApiOperation("删除一个收藏")
    @ApiImplicitParam(name = "id", value = "收藏主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse delete(String id);

    @ApiOperation("修改一个收藏")
    @ApiImplicitParam(name = "id", value = "收藏主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse update(String id, UpdateCollection updateCollection);

    @ApiOperation("根据用户id查询所有收藏信息")
    @ApiImplicitParam(name = "sUserID", value = "用户id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getListByUserID(String sUserID);

    @ApiOperation("获取收藏数量")
    @ApiImplicitParam(name = "sUserID", value = "用户id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getCountByUserID(String sUserID);

    @ApiOperation("根据条件删除一个收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sPositionID", value = "职位id", required = true,
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "sUserID", value = "用户id", required = true,
                    paramType = "path", dataType = "String")
    })
    CommonResponse deleteByCondition(String sPositionID, String sUserID);

    @ApiOperation("判断该用户是否收藏过此职位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sPositionID", value = "职位id", required = true,
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "sUserID", value = "用户id", required = true,
                    paramType = "path", dataType = "String")
    })
    QueryResponse getCount(String sPositionID, String sUserID);

}
