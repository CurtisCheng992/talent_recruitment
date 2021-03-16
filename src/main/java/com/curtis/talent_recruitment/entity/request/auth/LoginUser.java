package com.curtis.talent_recruitment.entity.request.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Curtis
 * @Description: 用户登录实体类
 * @Date: Created in 6:18 PM 3/9/2021
 */
@Data
public class LoginUser {

    @ApiModelProperty("用户名")
    private String sUsername;

    @ApiModelProperty("密码")
    private String sPassword;

    @ApiModelProperty("邮箱地址")
    private String sEmail;

    @ApiModelProperty("手机号码")
    private String sPhone;

    @ApiModelProperty("验证码")
    private String sCode;

    @ApiModelProperty("下次是否自动登录 true是 false不是")
    private boolean bRememberMe;

}
