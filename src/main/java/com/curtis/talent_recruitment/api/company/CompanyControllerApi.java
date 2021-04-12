package com.curtis.talent_recruitment.api.company;

import com.curtis.talent_recruitment.entity.request.company.AddCompany;
import com.curtis.talent_recruitment.entity.request.company.UpdateCompany;
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

    @ApiOperation("限制数量查询热门企业")
    @ApiImplicitParam(name = "iLimit", value = "查询条数", required = false,
            paramType = "query", dataType = "Integer")
    QueryResponse getHot(Integer iLimit);

    @ApiOperation("查询一个公司信息")
    @ApiImplicitParam(name = "sHRID", value = "HRID", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getDetailByHRID(String sHRID);

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

    @ApiOperation("审核或注销一个公司信息")
    @ApiImplicitParam(name = "id", value = "公司主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse updateStatus(String id);

    @ApiOperation("更新简历照片")
    @ApiImplicitParam(name = "id", value = "简历主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse updatePicture(String id, String sPicture);
}
