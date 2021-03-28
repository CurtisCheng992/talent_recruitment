package com.curtis.talent_recruitment.entity.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description: 用户登录实体类
 * @Date: Created in 6:18 PM 3/9/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginUser {

    @ApiModelProperty("用户名")
    @JsonProperty("sUsername")
    private String sUsername;

    @ApiModelProperty("密码")
    @JsonProperty("sPassword")
    private String sPassword;

    @ApiModelProperty("邮箱地址")
    @JsonProperty("sEmail")
    private String sEmail;

    @ApiModelProperty("手机号码")
    @JsonProperty("sPhone")
    private String sPhone;

    @ApiModelProperty("验证码")
    @JsonProperty("sCode")
    private String sCode;

    @ApiModelProperty("下次是否自动登录 true是 false不是")
    @JsonProperty("bRememberMe")
    private boolean bRememberMe;

}
