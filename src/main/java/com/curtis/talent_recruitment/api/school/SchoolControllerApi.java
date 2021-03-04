package com.curtis.talent_recruitment.api.school;

import com.curtis.talent_recruitment.entity.response.QueryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Curtis
 * @Description: 学校模块的api
 * @Date: Created in 3:46 PM 2/25/2021
 */
@Api(value = "学校模块管理接口", description = "学校模块管理接口，提供学校的查询")
public interface SchoolControllerApi {

    @ApiOperation("查询所有学校")
    QueryResponse getList();

    @ApiOperation("查询一所学校")
    @ApiImplicitParam(name = "id", value = "学校主键id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getDetail(String id);

}
