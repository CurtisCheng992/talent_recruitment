package com.curtis.talent_recruitment.api.user;

import com.curtis.talent_recruitment.entity.request.auth.LoginUser;
import com.curtis.talent_recruitment.entity.request.user.*;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description: 用户模块的api
 * @Date: Created in 10:19 AM 2/23/2021
 */
@Api(value = "用户模块管理接口", description = "用户模块管理接口，提供用户的增、删、改、查")
public interface UserControllerApi {

    @ApiOperation("查询所有用户信息")
    @ApiImplicitParam(name = "iRoleType", value = "角色类型", required = false,
            paramType = "query", dataType = "int")
    QueryResponse getList(Integer iRoleType);

    @ApiOperation("查询个人信息")
    @ApiImplicitParam(name = "id", value = "用户主键id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getDetail(String id);

    @ApiOperation("新增求职者用户")
    CommonResponse add(AddUser addUser);

    @ApiOperation("新增HR用户")
    CommonResponse addHR(AddHR addHR);

    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "id", value = "用户主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse delete(String id);

    @ApiOperation("修改个人信息")
    @ApiImplicitParam(name = "id", value = "用户主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse update(String id, UpdateUser updateUser);

    @ApiOperation("发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sendType", value = "发送验证码到何处 1手机 2邮箱", required = true,
                    paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "codeType", value = "因何发送验证码 1登录 2注册 3修改密码 4换绑手机/邮箱", required = true,
                    paramType = "path", dataType = "int")
    })
    CommonResponse sendCode(int sendType, int codeType, LoginUser loginUser);

    @ApiOperation("用户注册")
    CommonResponse register(RegisterUser registerUser);

    @ApiOperation("更换头像")
    @ApiImplicitParam(name = "id", value = "用户主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse updateAvatar(String id, AvatarUser avatarUser, HttpServletRequest request, HttpServletResponse response);

    @ApiOperation("修改密码")
    @ApiImplicitParam(name = "id", value = "用户主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse updatePassword(String id, PasswordUser passwordUser, HttpServletRequest request);

    @ApiOperation("换绑邮箱")
    @ApiImplicitParam(name = "id", value = "用户主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse updateEmail(String id, EmailUser emailUser, HttpServletRequest request);

    @ApiOperation("换绑手机号码")
    @ApiImplicitParam(name = "id", value = "用户主键id", required = true,
            paramType = "path", dataType = "String")
    CommonResponse updatePhone(String id, PhoneUser phoneUser, HttpServletRequest request);

    @ApiOperation("根据用户id获取邮箱地址")
    @ApiImplicitParam(name = "id", value = "用户主键id", required = true,
            paramType = "path", dataType = "String")
    QueryResponse getEmailById(String id);

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
