package com.curtis.talent_recruitment.entity.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:17 PM 3/16/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterUser {

    @ApiModelProperty(value = "用户名", required = true, dataType = "String")
    @JsonProperty("sUsername")
    private String sUsername;

    @ApiModelProperty(value = "密码", required = true, dataType = "String")
    @JsonProperty("sPassword")
    private String sPassword;

    @ApiModelProperty(value = "角色类型：1超级管理员 2HR 3普通求职者", required = true, dataType = "int")
    @JsonProperty("iRoleType")
    private int iRoleType;

    @ApiModelProperty(value = "真实姓名", required = true, dataType = "String")
    @JsonProperty("sRealName")
    private String sRealName;

    @ApiModelProperty(value = "手机号码", required = true, dataType = "String")
    @JsonProperty("sPhone")
    private String sPhone;

    @ApiModelProperty(value = "电子邮箱", required = true, dataType = "String")
    @JsonProperty("sEmail")
    private String sEmail;

    @ApiModelProperty(value = "性别：0女 1男", dataType = "int")
    @JsonProperty("iGender")
    private int iGender;

    @ApiModelProperty(value = "年龄", dataType = "int")
    @JsonProperty("iAge")
    private int iAge;

    @ApiModelProperty(value = "头像", dataType = "String")
    @JsonProperty("sAvatar")
    private String sAvatar;

    @ApiModelProperty(value = "省份", dataType = "String")
    @JsonProperty("sProvince")
    private String sProvince;

    @ApiModelProperty(value = "城市", dataType = "String")
    @JsonProperty("sCity")
    private String sCity;

    @ApiModelProperty(value = "毕业年份", dataType = "int")
    @JsonProperty("iGraduationYear")
    private int iGraduationYear;

    @ApiModelProperty(value = "专业", dataType = "String")
    @JsonProperty("sMajor")
    private String sMajor;

    @ApiModelProperty(value = "学历", dataType = "String")
    @JsonProperty("sEducation")
    private String sEducation;

    @ApiModelProperty(value = "学校id", dataType = "String")
    @JsonProperty("sSchoolID")
    private String sSchoolID;

    @ApiModelProperty(value = "就业方向", dataType = "String")
    @JsonProperty("sDirection")
    private String sDirection;

    @ApiModelProperty(value = "描述", dataType = "String")
    @JsonProperty("sDescription")
    private String sDescription;

    @ApiModelProperty(value = "验证码", dataType = "String")
    @JsonProperty("code")
    private String code;

}
