package com.curtis.talent_recruitment.api.auth;

import com.curtis.talent_recruitment.entity.request.auth.LoginUser;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Curtis
 * @Description: 认证模块的api
 * @Date: Created in 6:14 PM 3/9/2021
 */
@Api(value = "认证模块接口", description = "认证模块接口，提供认证服务接口")
public interface AuthControllerApi {

    @ApiOperation("根据不同登录方式进行用户授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userType", value = "用户类型 1管理员 2普通用户", required = true,
                    paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "loginType", value = "登录方式 1账号登录 2邮箱登录 3手机号码登录", required = true,
                    paramType = "path", dataType = "int")
    })
    CommonResponse login(int userType, int loginType, LoginUser loginUser,
                         HttpServletRequest request, HttpServletResponse response);

    @ApiOperation("根据不同用户携带的 token 信息验证用户身份")
    @ApiImplicitParam(name = "userType", value = "用户类型 1管理员 2普通用户", required = true,
            paramType = "path", dataType = "int")
    CommonResponse verify(int userType, HttpServletRequest request, HttpServletResponse response);

    @ApiOperation("用户退出登录")
    @ApiImplicitParam(name = "userType", value = "用户类型 1管理员 2普通用户", required = true,
            paramType = "path", dataType = "int")
    CommonResponse logout(int userType, HttpServletRequest request, HttpServletResponse response);

    @ApiOperation("用户访问计数")
    CommonResponse viewCount();

    @ApiOperation("获取网站统计数据")
    QueryResponse findWebDataCount();

    @ApiOperation("获取网站每小时的访问量")
    QueryResponse findViewCountPerHour();

}
