package com.curtis.talent_recruitment.auth.service.impl;

import com.curtis.talent_recruitment.auth.entity.UserInfo;
import com.curtis.talent_recruitment.auth.service.IAuthService;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.entity.response.result.WebDataCount;
import com.curtis.talent_recruitment.user.dao.UserDao;
import com.curtis.talent_recruitment.user.entity.User;
import com.curtis.talent_recruitment.utils.auth.JwtConfig;
import com.curtis.talent_recruitment.utils.auth.JwtUtils;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 6:07 PM 3/9/2021
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtConfig config;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String LOGIN_KEY_PREFIX = "user:code:login:";

    private static final Logger LOGGER = LoggerFactory.getLogger( AuthServiceImpl.class );

    @Override
    public String authByPwd(String username, String password, boolean rememberMe, int userType) {
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("sUsername",username);
        mpParam.put("sPassword",password);
        User user = this.userDao.getDetail( mpParam );
        return getToken( user, rememberMe, userType );
    }

    @Override
    public String authByEmail(String email, String code, boolean rememberMe, int userType) {
        // 验证码校验
        if (!StringUtils.equals( code, this.redisTemplate.opsForValue().get( LOGIN_KEY_PREFIX + email ) )) {
            return null;
        }
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("sEmail",email);
        User user = this.userDao.getDetail( mpParam );

        return getToken( user, rememberMe, userType );
    }

    @Override
    public String authByPhone(String phone, String code, boolean rememberMe, int userType) {
        // 验证码校验
        if (!StringUtils.equals( code, this.redisTemplate.opsForValue().get( LOGIN_KEY_PREFIX + phone ) )) {
            return null;
        }
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("sPhone",phone);
        User user = this.userDao.getDetail( mpParam );

        return getToken( user, rememberMe, userType );
    }

    @Override
    public CommonResponse viewCount() {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyy-MM-dd:HH" );
        String dateKey = "count:vi:" + sdf.format( new Date() );
        String hourKey = "count:vi:" + sdf2.format( new Date() );
        redisTemplate.opsForValue().setIfAbsent( dateKey, "0", 25L, TimeUnit.HOURS );
        redisTemplate.opsForValue().setIfAbsent( hourKey, "0", 25L, TimeUnit.HOURS );
        redisTemplate.opsForValue().increment( dateKey );
        redisTemplate.opsForValue().increment( hourKey );
        redisTemplate.opsForValue().increment( "count:vi" );
        return CommonResponse.SUCCESS();
    }

    private String getToken(User user, boolean rememberMe, int userType) {
        if (user == null) {
            return null;
        }
        // 校验是否合法登录
        if (!checkIfValid( userType, user.getIRoleType() )) {
            return null;
        }
        String token = null;
        // 如果有查询结果，则生成token
        try {
            token = JwtUtils.generateToken( new UserInfo( user.getId(), user.getSUsername(),
                            user.getIRoleType() == 2, user.getSAvatar(), rememberMe, user.getIStatus() ),
                    config.getPrivateKey(), config.getExpire() );
        } catch (Exception e) {
            LOGGER.error( "生成 token 发生异常！异常原因：{}", e.getMessage() );
            ExceptionThrowUtils.cast( CommonCode.SERVER_ERROR );
        }
        return token;
    }

    private boolean checkIfValid(int userType, int roleId) {
        switch (userType) {
            case 1: // 管理员登录
                if (roleId != 1) {
                    return false;
                }
                break;
            case 2: // 普通用户登录
                if (roleId != 2 && roleId != 3) {
                    return false;
                }
        }
        return true;
    }

    @Override
    public QueryResponse webDataCount() {
        String currentDate = new SimpleDateFormat( "yyyy-MM-dd" ).format( new Date() );
        String dateVisitCount = this.redisTemplate.opsForValue().get( "count:vi:" + currentDate );
        String dateRegisterCount = this.redisTemplate.opsForValue().get( "count:rg:" + currentDate );
        String dateDownloadCount = this.redisTemplate.opsForValue().get( "count:dl:" + currentDate );
        WebDataCount webDataCount = WebDataCount.builder()
                .totalVisitCount( Integer.valueOf( Objects.requireNonNull( this.redisTemplate.opsForValue().get( "count:vi" ) ) ) )
                .dateVisitCount( Integer.valueOf( dateVisitCount == null ? "0" : dateVisitCount ) )
                .totalDownloadCount( Integer.valueOf( Objects.requireNonNull( this.redisTemplate.opsForValue().get( "count:dl" ) ) ) )
                .dateDownloadCount( Integer.valueOf( dateDownloadCount == null ? "0" : dateDownloadCount ) )
                .totalRegisterCount( Integer.valueOf( Objects.requireNonNull( this.redisTemplate.opsForValue().get( "count:rg" ) ) ) )
                .dateRegisterCount( Integer.valueOf( dateRegisterCount == null ? "0" : dateRegisterCount ) )
                .build();
        return new QueryResponse( CommonCode.SUCCESS, new QueryResult<>( Collections.singletonList( webDataCount ), 1 ) );
    }

    @Override
    public QueryResponse perHourViewCount() {
        String currentDate = new SimpleDateFormat( "yyyy-MM-dd" ).format( new Date() );
        List<Integer> hourViewCounts = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            String hour = i < 10 ? "0" + i : String.valueOf( i );
            String hourViewCount = redisTemplate.opsForValue().get( "count:vi:" + currentDate + ":" + hour );
            hourViewCounts.add( Integer.valueOf( hourViewCount == null ? "0" : hourViewCount ) );
        }
        return new QueryResponse( CommonCode.SUCCESS, new QueryResult<>( hourViewCounts, 24 ) );
    }

}
