package com.curtis.talent_recruitment.api.resume;

import com.curtis.talent_recruitment.entity.request.position.AddPosition;
import com.curtis.talent_recruitment.entity.request.position.UpdatePosition;
import com.curtis.talent_recruitment.entity.request.resume.AddResume;
import com.curtis.talent_recruitment.entity.request.resume.UpdateResume;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:58 PM 3/1/2021
 */
@Api(value = "简历模块管理接口", description = "简历模块管理接口，提供简历的增、删、改、查")
public interface ResumeControllerApi {

    @ApiOperation("查询所有简历信息")
    QueryResponse getList();

    @ApiOperation("查询一个简历信息")
    @ApiImplicitParam(name = "id", value = "简历主键id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getDetail(String id);

    @ApiOperation("新增一个简历")
    CommonResponse add(AddResume addResume);

    @ApiOperation("删除一个简历")
    @ApiImplicitParam(name = "id", value = "简历主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse delete(String id);

    @ApiOperation("修改一个简历")
    @ApiImplicitParam(name = "id", value = "简历主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse update(String id, UpdateResume updateResume);

    @ApiOperation("根据用户id查找一个简历信息")
    @ApiImplicitParam(name = "sUserID", value = "用户id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getByUserID(String sUserID);
}
