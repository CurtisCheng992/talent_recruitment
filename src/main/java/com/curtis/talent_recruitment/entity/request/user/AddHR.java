package com.curtis.talent_recruitment.entity.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:53 PM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddHR {

    @JsonProperty("sUsername")
    private String sUsername; //用户名
    @JsonProperty("sPassword")
    private String sPassword; //密码
    @JsonProperty("sRealName")
    private String sRealName; //真实姓名
    @JsonProperty("sPhone")
    private String sPhone; //电话号码
    @JsonProperty("sEmail")
    private String sEmail; //电子邮箱
    @JsonProperty("iGender")
    private Integer iGender; //性别
    @JsonProperty("iAge")
    private Integer iAge; //年龄
    @JsonProperty("sAvatar")
    private String sAvatar; //头像
    @JsonProperty("sProvince")
    private String sProvince; //省份
    @JsonProperty("sCity")
    private String sCity; //城市
    @JsonProperty("sDescription")
    private String sDescription; //描述

}
