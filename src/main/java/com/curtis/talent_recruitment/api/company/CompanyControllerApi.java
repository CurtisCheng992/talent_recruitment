package com.curtis.talent_recruitment.api.company;

import com.curtis.talent_recruitment.entity.request.company.AddCompany;
import com.curtis.talent_recruitment.entity.request.company.UpdateCompany;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:37 PM 3/1/2021
 */
@Api(value = "公司模块管理接口", description = "公司模块管理接口，提供公司的增、删、改、查")
public interface CompanyControllerApi {

    @ApiOperation("查询所有公司信息")
    QueryResponse getList();

    @ApiOperation("查询一个公司信息")
    @ApiImplicitParam(name = "id", value = "公司主键id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getDetail(String id);

    @ApiOperation("新增一个公司")
    CommonResponse add(AddCompany addCompany);

    @ApiOperation("删除一个公司")
    @ApiImplicitParam(name = "id", value = "公司主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse delete(String id);

    @ApiOperation("修改一个公司")
    @ApiImplicitParam(name = "id", value = "公司主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse update(String id, UpdateCompany updateCompany);

}
