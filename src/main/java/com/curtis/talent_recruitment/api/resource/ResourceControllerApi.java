package com.curtis.talent_recruitment.api.resource;

import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.UploadResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 12:44 PM 3/27/2021
 */
@Api(value = "资源模块管理接口", description = "资源模块管理接口，提供申请的增、删、改、查")
public interface ResourceControllerApi {

    @ApiOperation("根据文件存储路径删除图片")
    @ApiImplicitParam(name = "sLocation", value = "文件存储路径", required = true,
            paramType = "query", dataType = "String")
    CommonResponse deleteByLocation(String sLocation) throws UnsupportedEncodingException;

    @ApiOperation("删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sResourceID", value = "资源id", required = true,
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "sUserID", value = "用户id", required = true,
                    paramType = "path", dataType = "String"),
    })
    CommonResponse delete(String sResourceID, String sUserID);

    @ApiOperation("上传文件")
    @ApiImplicitParam(name = "sUserID", value = "用户id", required = true,
            paramType = "path", dataType = "String")
    UploadResponse upload(MultipartFile file, String sUserID);

    @ApiOperation("下载文件")
    @ApiImplicitParam(name = "sResourceID", value = "资源id", required = true,
            paramType = "path", dataType = "String")
    void download(String sResourceID, HttpServletResponse response);

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
