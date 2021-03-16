package com.curtis.talent_recruitment.utils.auth;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * JWT配置类
 */
@ConfigurationProperties(prefix = "tr.jwt")
@Data
public class JwtConfig {
    private String secret; // 密钥

    private String pubKeyPath; // 公钥

    private String priKeyPath; // 私钥

    private int expire; // token过期时间

    private PublicKey publicKey; // 公钥

    private PrivateKey privateKey; // 私钥

    private String userCookieName;  // 普通用户鉴权的 cookie 名称

    private String adminCookieName;  // 管理员鉴权的 cookie 名称

    private int cookieMaxAge; // cookie 最大存活时间

    private static final Logger LOGGER = LoggerFactory.getLogger( JwtConfig.class );


    @PostConstruct
    public void init() {
        try {
            File pubKey = new File( pubKeyPath );
            File priKey = new File( priKeyPath );
            if (!pubKey.exists() || !priKey.exists()) {
                // 生成公钥和私钥
                RsaUtils.generateKey( pubKeyPath, priKeyPath, secret );
            }
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey( pubKeyPath );
            this.privateKey = RsaUtils.getPrivateKey( priKeyPath );
        } catch (Exception e) {
            LOGGER.error( "初始化公钥和私钥失败！", e );
            throw new RuntimeException();
        }
    }
}
