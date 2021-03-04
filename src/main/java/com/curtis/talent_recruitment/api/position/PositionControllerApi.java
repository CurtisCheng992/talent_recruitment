package com.curtis.talent_recruitment.api.position;

import com.curtis.talent_recruitment.entity.request.position.AddPosition;
import com.curtis.talent_recruitment.entity.request.position.UpdatePosition;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:17 PM 3/1/2021
 */
@Api(value = "职位模块管理接口", description = "职位模块管理接口，提供职位的增、删、改、查")
public interface PositionControllerApi {

    @ApiOperation("查询所有职位信息")
    QueryResponse getList();

    @ApiOperation("查询一个职位信息")
    @ApiImplicitParam(name = "id", value = "职位主键id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getDetail(String id);

    @ApiOperation("新增一个职位")
    CommonResponse add(AddPosition addPosition);

    @ApiOperation("删除一个职位")
    @ApiImplicitParam(name = "id", value = "职位主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse delete(String id);

    @ApiOperation("修改一个职位")
    @ApiImplicitParam(name = "id", value = "职位主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse update(String id, UpdatePosition updatePosition);

}
