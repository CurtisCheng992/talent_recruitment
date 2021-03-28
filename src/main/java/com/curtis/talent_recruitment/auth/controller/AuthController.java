package com.curtis.talent_recruitment.auth.controller;

import com.curtis.talent_recruitment.api.auth.AuthControllerApi;
import com.curtis.talent_recruitment.auth.entity.UserInfo;
import com.curtis.talent_recruitment.auth.service.IAuthService;
import com.curtis.talent_recruitment.entity.request.auth.LoginUser;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.auth.AuthCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.utils.auth.CookieUtils;
import com.curtis.talent_recruitment.utils.auth.JwtConfig;
import com.curtis.talent_recruitment.utils.auth.JwtUtils;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:42 PM 3/9/2021
 */
@RestController
@EnableConfigurationProperties(JwtConfig.class)
@RequestMapping("auth")
public class AuthController implements AuthControllerApi {

    @Autowired
    private IAuthService authService;

    @Autowired
    private JwtConfig config;

    private static final Logger LOGGER = LoggerFactory.getLogger( AuthController.class );

    private final List<Integer> userTypeList = Arrays.asList( 1, 2 );

    private final List<Integer> loginTypeList = Arrays.asList( 1, 2, 3 );

    /**
     * 处理用户登录逻辑
     *
     * @param userType  用户类型 1管理员 2普通用户
     * @param loginType 登录方式 1账号登录 2邮箱登录 3手机号登录
     * @param loginUser 登录用户实体类
     * @param request   request
     * @param response  response
     * @return 响应实体类
     */
    @Override
    @PostMapping("login/{userType}/{loginType}")
    public CommonResponse login(@PathVariable(name = "userType") int userType,
                                @PathVariable(name = "loginType") int loginType,
                                @RequestBody LoginUser loginUser,
                                HttpServletRequest request, HttpServletResponse response) {
        // loginType userType loginUser 非法
        if (!userTypeList.contains( userType ) || !loginTypeList.contains( loginType ) || loginUser == null) {
            ExceptionThrowUtils.cast( CommonCode.INVALID_PARAM );
        }
        String token = null;
        switch (loginType) {
            case 1: // 账号登录
                // 登录校验
                token = this.authService.authByPwd( loginUser.getSUsername(), loginUser.getSPassword(), loginUser.isBRememberMe(), userType );
                if (StringUtils.isBlank( token )) {
                    return new CommonResponse( AuthCode.LOGIN_FAIL_BY_PWD );
                }
                break;
            case 2: // 邮箱登录
                // 登录校验
                token = this.authService.authByEmail( loginUser.getSEmail(), loginUser.getSCode(), loginUser.isBRememberMe(), userType );
                if (StringUtils.isBlank( token )) {
                    return new CommonResponse( AuthCode.LOGIN_FAIL_BY_EMAIL );
                }
                break;
            case 3: // 手机号登录
                // 登录校验
                token = this.authService.authByPhone( loginUser.getSPhone(), loginUser.getSCode(), loginUser.isBRememberMe(), userType );
                if (StringUtils.isBlank( token )) {
                    return new CommonResponse( AuthCode.LOGIN_FAIL_BY_PHONE );
                }
        }
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken( token, config.getPublicKey() );
            if (userInfo.getIStatus()==0) {
                return new CommonResponse( AuthCode.INVALID_USER );
            }
        } catch (Exception e) {
            LOGGER.error( "获取token中的用户信息异常！异常原因：{}", e );
        }
        // 将 token 写入 cookie ,并指定 httpOnly 为 true ，防止通过 JS 获取和修改
        String cookieName = userType == 1 ? config.getAdminCookieName() : config.getUserCookieName();
        CookieUtils.setCookie( request, response, cookieName, token,
                // 如果用户勾选了记住我，则将 cookie 存活时间设置为 7 天
                loginUser.isBRememberMe() ? 14 * config.getCookieMaxAge() : config.getCookieMaxAge(),
                null, true );
        return new CommonResponse( AuthCode.LOGIN_SUCCESS );
    }

    /**
     * 根据不同用户携带的 token 信息验证用户身份
     *
     * @param userType 登录用户类型 1管理员 2普通用户
     * @param request  request
     * @param response response
     * @return 响应实体类
     */
    @Override
    @GetMapping("verify/{userType}")
    public CommonResponse verify(@PathVariable int userType, HttpServletRequest request, HttpServletResponse response) {
        if (!userTypeList.contains( userType )) {
            return new CommonResponse( CommonCode.INVALID_PARAM );
        }
        String cookieName = userType == 1 ? config.getAdminCookieName() : config.getUserCookieName();
        String token = CookieUtils.getCookieValue( request, cookieName );
        try {
            // 解析 token
            UserInfo userInfo = JwtUtils.getInfoFromToken( token, config.getPublicKey() );
            // 解析成功要重新刷新 token
            token = JwtUtils.generateToken( userInfo, config.getPrivateKey(), config.getExpire() );
            // 更新 cookie 中的 token
            CookieUtils.setCookie( request, response, cookieName, token,
                    userInfo.getBRememberMe() ? 14 * config.getCookieMaxAge() : config.getCookieMaxAge(),
                    null, true );

            return new QueryResponse( AuthCode.VERIFY_SUCCESS,
                    new QueryResult<>( Collections.singletonList( userInfo ), 1 ) );
        }catch (IllegalArgumentException iae){
            System.out.println("JWT String argument cannot be null or empty.");
        }catch (Exception e) {
            LOGGER.error( "获取token中的用户信息异常！异常原因：{}", e );
        }
        return new CommonResponse( CommonCode.SERVER_ERROR );
    }

    /**
     * 用户退出登录
     *
     * @param request  request
     * @param response response
     * @return 响应结果实体类
     */
    @Override
    @DeleteMapping("logout/{userType}")
    public CommonResponse logout(@PathVariable int userType, HttpServletRequest request, HttpServletResponse response) {
        String cookieName = userType == 1 ? config.getAdminCookieName() : config.getUserCookieName();
        CookieUtils.setCookie( request, response, cookieName, "VALID VALUE",
                1, null, true );
        return new CommonResponse( CommonCode.SUCCESS );
    }

    @Override
    @PutMapping("viewCount")
    public CommonResponse viewCount() {
        return authService.viewCount();
    }

    @Override
    @GetMapping("webDataCount")
    public QueryResponse findWebDataCount() {
        return authService.webDataCount();
    }

    @Override
    @GetMapping("perHourViewCount")
    public QueryResponse findViewCountPerHour() {
        return authService.perHourViewCount();
    }
}
