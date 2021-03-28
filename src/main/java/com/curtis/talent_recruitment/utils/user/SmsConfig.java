package com.curtis.talent_recruitment.utils.user;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Curtis
 * @version 1.0.0
 * @description sms 配置类
 * @date 2021/3/16 17:22
 */
@ConfigurationProperties(prefix = "tr.sms")
@Data
@Component
public class SmsConfig {

    private String accessKeyId;

    private String accessKeySecret;

    private String signName;

    private String verifyCodeTemplate;
}
