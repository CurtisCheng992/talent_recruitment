package com.curtis.talent_recruitment.api.position;

import com.curtis.talent_recruitment.entity.request.position.AddPosition;
import com.curtis.talent_recruitment.entity.request.position.UpdatePosition;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:17 PM 3/1/2021
 */
@Api(value = "职位模块管理接口", description = "职位模块管理接口，提供职位的增、删、改、查")
public interface PositionControllerApi {

    @ApiOperation("可根据条件查询所有职位信息")
    @ApiImplicitParam(name = "sHRID", value = "HR的id", required = false,
            paramType = "query", dataType = "String")
    QueryResponse getList(String sHRID);

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

    @ApiOperation("查询热门职位")
    @ApiImplicitParam(name = "iLimit", value = "查询条数", required = false,
            paramType = "query", dataType = "Integer")
    QueryResponse getHot(Integer iLimit);

    @ApiOperation("查询最新职位")
    @ApiImplicitParam(name = "iLimit", value = "查询条数", required = false,
            paramType = "query", dataType = "Integer")
    QueryResponse getNew(Integer iLimit);

    @ApiOperation("搜索职位")
    @ApiImplicitParam(name = "sPositionName", value = "职位名称", required = false,
            paramType = "query", dataType = "String")
    QueryResponse getSearch(String sPositionName);

    @ApiOperation("根据HRID统计发布的职位数量")
    @ApiImplicitParam(name = "sHRID", value = "HRID", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getCount(String sHRID);

    @ApiOperation("根据分类号查询职位信息")
    @ApiImplicitParam(name = "sCategoryID", value = "分类id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getListByCategoryID(String sCategoryID);

}
