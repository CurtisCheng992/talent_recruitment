package com.curtis.talent_recruitment.api.application;

import com.curtis.talent_recruitment.entity.request.application.AddApplication;
import com.curtis.talent_recruitment.entity.request.application.UpdateApplication;
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
 * @Date: Created in 7:06 PM 3/1/2021
 */
@Api(value = "申请模块管理接口", description = "申请模块管理接口，提供申请的增、删、改、查")
public interface ApplicationControllerApi {

    @ApiOperation("查询所有申请信息")
    QueryResponse getList();

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
    
}
