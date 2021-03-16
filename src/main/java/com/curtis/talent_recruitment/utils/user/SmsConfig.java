package com.curtis.talent_recruitment.utils.user;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Colin
 * @version 1.0.0
 * @description sms 配置类
 * @date 2020/10/29 17:22
 */
@ConfigurationProperties(prefix = "yxt.sms")
@Data
public class SmsConfig {

    private String accessKeyId;

    private String accessKeySecret;

    private String signName;

    private String verifyCodeTemplate;
}
