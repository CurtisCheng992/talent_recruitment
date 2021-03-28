package com.curtis.talent_recruitment.api.user;

import com.curtis.talent_recruitment.entity.request.auth.LoginUser;
import com.curtis.talent_recruitment.entity.request.user.AddHR;
import com.curtis.talent_recruitment.entity.request.user.AddUser;
import com.curtis.talent_recruitment.entity.request.user.RegisterUser;
import com.curtis.talent_recruitment.entity.request.user.UpdateUser;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Curtis
 * @Description: 用户模块的api
 * @Date: Created in 10:19 AM 2/23/2021
 */
@Api(value = "用户模块管理接口", description = "用户模块管理接口，提供用户的增、删、改、查")
public interface UserControllerApi {

    @ApiOperation("查询所有用户信息")
    QueryResponse getList();

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

}
