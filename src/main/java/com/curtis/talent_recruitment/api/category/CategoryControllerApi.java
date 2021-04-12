package com.curtis.talent_recruitment.api.category;

import com.curtis.talent_recruitment.entity.request.school.AddCategory;
import com.curtis.talent_recruitment.entity.request.school.UpdateCategory;
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
 * @Date: Created in 4:10 PM 2/25/2021
 */
@Api(value = "分类模块管理接口", description = "分类模块管理接口，提供分类的增、删、改、查")
public interface CategoryControllerApi {

    @ApiOperation("查询所有分类信息")
    QueryResponse getList();

    @ApiOperation("查询一个分类信息")
    @ApiImplicitParam(name = "id", value = "分类主键id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getDetail(String id);

    @ApiOperation("新增一个分类")
    CommonResponse add(AddCategory addCategory);

    @ApiOperation("删除一个分类")
    @ApiImplicitParam(name = "id", value = "分类主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse delete(String id);

    @ApiOperation("修改一个分类")
    @ApiImplicitParam(name = "id", value = "分类主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse update(String id, UpdateCategory updateCategory);

    @ApiOperation("查询一个分类信息")
    @ApiImplicitParam(name = "sCategoryName", value = "分类名称", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getByName(String sCategoryName);

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
