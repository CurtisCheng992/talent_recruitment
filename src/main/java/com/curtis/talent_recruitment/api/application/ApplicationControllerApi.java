package com.curtis.talent_recruitment.api.application;

import com.curtis.talent_recruitment.entity.request.application.AddApplication;
import com.curtis.talent_recruitment.entity.request.application.UpdateApplication;
import com.curtis.talent_recruitment.entity.request.resume.AddResume;
import com.curtis.talent_recruitment.entity.request.resume.UpdateResume;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:06 PM 3/1/2021
 */
@Api(value = "申请模块管理接口", description = "申请模块管理接口，提供申请的增、删、改、查")
public interface ApplicationControllerApi {

    @ApiOperation("查询所有申请信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sUserID", value = "用户id", required = false,
                    paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sHRID", value = "HR的id", required = false,
                    paramType = "query", dataType = "String")
    })
    QueryResponse getList(String sUserID, String sHRID);

    @ApiOperation("查询一个申请信息")
    @ApiImplicitParam(name = "id", value = "申请主键id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getDetail(String id);

    @ApiOperation("新增一个申请")
    CommonResponse add(AddApplication addApplication);

    @ApiOperation("删除一个申请")
    @ApiImplicitParam(name = "id", value = "申请主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse delete(String id);

    @ApiOperation("修改一个申请")
    @ApiImplicitParam(name = "id", value = "申请主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse update(String id, UpdateApplication updateApplication);

    @ApiOperation("根据用户id查询所有申请信息")
    @ApiImplicitParam(name = "sUserID", value = "用户id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getListByUserID(String sUserID);

    @ApiOperation("根据用户id查询申请信息条数")
    @ApiImplicitParam(name = "sUserID", value = "用户id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getCountByUserID(String sUserID);

    @ApiOperation("判断该用户是否申请过此职位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sPositionID", value = "职位id", required = true,
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "sUserID", value = "用户id", required = true,
                    paramType = "path", dataType = "String")
    })
    QueryResponse getCount(String sPositionID, String sUserID);

    @ApiOperation("根据条件删除一个申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sPositionID", value = "职位id", required = true,
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "sUserID", value = "用户id", required = true,
                    paramType = "path", dataType = "String")
    })
    CommonResponse deleteByCondition(String sPositionID, String sUserID);

    @ApiOperation("HR完成申请审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sHRID", value = "HRID", required = true,
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "申请id", required = true,
                    paramType = "path", dataType = "String")
    })
    CommonResponse VerifyByID(String sHRID, String id);

    @ApiOperation("根据HRID统计申请数量")
    @ApiImplicitParam(name = "sHRID", value = "HRID", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getCountByHR(String sHRID);

    @ApiOperation("根据条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lCurrentPage", value = "当前分页", required = true,
                    paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "lPageSize", value = "分页大小", required = true,
                    paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "mpParam", value = "参数", required = false,
                    paramType = "body", dataType = "Map")
    })
    QueryResponse getByPage(Long lCurrentPage, Long lPageSize, Map<String, Object> mpParam);

}
