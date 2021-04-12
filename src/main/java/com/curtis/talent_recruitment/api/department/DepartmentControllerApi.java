package com.curtis.talent_recruitment.api.department;

import com.curtis.talent_recruitment.entity.request.department.AddDepartment;
import com.curtis.talent_recruitment.entity.request.department.UpdateDepartment;
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
 * @Date: Created in 4:31 PM 3/1/2021
 */
@Api(value = "部门模块管理接口", description = "部门模块管理接口，提供部门的增、删、改、查")
public interface DepartmentControllerApi {

    @ApiOperation("查询所有部门信息")
    QueryResponse getList();

    @ApiOperation("查询一个部门信息")
    @ApiImplicitParam(name = "id", value = "部门主键id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getDetail(String id);

    @ApiOperation("新增一个部门")
    CommonResponse add(AddDepartment addDepartment);

    @ApiOperation("删除一个部门")
    @ApiImplicitParam(name = "id", value = "部门主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse delete(String id);

    @ApiOperation("修改一个部门")
    @ApiImplicitParam(name = "id", value = "部门主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse update(String id, UpdateDepartment updateDepartment);

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
