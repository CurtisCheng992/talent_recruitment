package com.curtis.talent_recruitment.utils.auth;

import com.curtis.talent_recruitment.auth.entity.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * JWT工具类，负责生成token、解析token、从token中获取用户信息
 */
public class JwtUtils {
    /**
     * 私钥加密token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(UserInfo userInfo, PrivateKey privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim( JwtConstant.JWT_KEY_ID, userInfo.getId() )
                .claim( JwtConstant.JWT_KEY_USERNAME, userInfo.getSUsername() )
                .claim( JwtConstant.JWT_KEY_IS_HR, userInfo.getIsHR() )
                .claim( JwtConstant.JWT_KEY_AVATAR, userInfo.getSAvatar() )
                .claim( JwtConstant.JWT_KEY_REMEMBER_ME, userInfo.getBRememberMe() )
                .claim( JwtConstant.JWT_KEY_STATUS, userInfo.getIStatus() )
                .claim( JwtConstant.JWT_KEY_SREALNAME, userInfo.getSRealName() )
                .setExpiration( DateTime.now().plusMinutes( expireMinutes ).toDate() )
                .signWith( SignatureAlgorithm.RS256, privateKey )
                .compact();
    }

    /**
     * 私钥加密token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥字节数组
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(UserInfo userInfo, byte[] privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim( JwtConstant.JWT_KEY_ID, userInfo.getId() )
                .claim( JwtConstant.JWT_KEY_USERNAME, userInfo.getSUsername() )
                .claim( JwtConstant.JWT_KEY_IS_HR, userInfo.getIsHR() )
                .claim( JwtConstant.JWT_KEY_AVATAR, userInfo.getSAvatar() )
                .claim( JwtConstant.JWT_KEY_REMEMBER_ME, userInfo.getBRememberMe() )
                .claim( JwtConstant.JWT_KEY_STATUS, userInfo.getIStatus() )
                .claim( JwtConstant.JWT_KEY_SREALNAME, userInfo.getSRealName() )
                .setExpiration( DateTime.now().plusMinutes( expireMinutes ).toDate() )
                .signWith( SignatureAlgorithm.RS256, RsaUtils.getPrivateKey( privateKey ) )
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey( publicKey ).parseClaimsJws( token );
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥字节数组
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, byte[] publicKey) throws Exception {
        return Jwts.parser().setSigningKey( RsaUtils.getPublicKey( publicKey ) )
                .parseClaimsJws( token );
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static UserInfo getInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken( token, publicKey );
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtils.toString( body.get( JwtConstant.JWT_KEY_ID ) ),
                ObjectUtils.toString( body.get( JwtConstant.JWT_KEY_USERNAME ) ),
                ObjectUtils.toBoolean( body.get( JwtConstant.JWT_KEY_IS_HR ) ),
                ObjectUtils.toString( body.get( JwtConstant.JWT_KEY_AVATAR ) ),
                ObjectUtils.toBoolean( body.get( JwtConstant.JWT_KEY_REMEMBER_ME ) ),
                ObjectUtils.toInt( body.get( JwtConstant.JWT_KEY_STATUS ) ),
                ObjectUtils.toString( body.get( JwtConstant.JWT_KEY_SREALNAME ) ) );
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static UserInfo getInfoFromToken(String token, byte[] publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken( token, publicKey );
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtils.toString( body.get( JwtConstant.JWT_KEY_ID ) ),
                ObjectUtils.toString( body.get( JwtConstant.JWT_KEY_USERNAME ) )
        );
    }

    /**
     * @author Colin
     * @version 1.0.0
     * @description JWT被生成token的常量
     * @date 2020/10/23 19:40
     */
    class JwtConstant {

        static final String JWT_KEY_ID = "id";
        static final String JWT_KEY_USERNAME = "sUsername";
        static final String JWT_KEY_AVATAR = "sAvatar";
        static final String JWT_KEY_IS_HR = "isHR";
        static final String JWT_KEY_REMEMBER_ME = "bRememberMe";
        static final String JWT_KEY_STATUS = "iStatus";
        static final String JWT_KEY_SREALNAME = "sRealName";

    }
}
