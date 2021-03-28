package com.curtis.talent_recruitment.entity.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:51 PM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddUser {

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
    @JsonProperty("iGraduationYear")
    private Integer iGraduationYear; //毕业年份
    @JsonProperty("sMajor")
    private String sMajor; //专业
    @JsonProperty("sEducation")
    private String sEducation; //学历
    @JsonProperty("sSchoolID")
    private String sSchoolID; //学校id
    @JsonProperty("sSchoolName")
    private String sSchoolName; //学校名称
    @JsonProperty("sDirection")
    private String sDirection; //就业方向
    @JsonProperty("sDescription")
    private String sDescription; //描述

}
