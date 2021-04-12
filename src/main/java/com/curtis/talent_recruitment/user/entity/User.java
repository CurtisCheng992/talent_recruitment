package com.curtis.talent_recruitment.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description: 用户实体类
 * @Date: Created in 10:27 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
@ToString
public class User {

    private String id; //主键UUID
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
    private Integer iGender; //性别：0女 1男
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
    private String sEducation; //学历 {小学、初中、中专/高中、专科、本科、研究生}
    @JsonProperty("sSchoolID")
    private String sSchoolID; //学校id
    @JsonProperty("sSchoolName")
    private String sSchoolName; //学校名字
    @JsonProperty("iRoleType")
    private Integer iRoleType; //角色类型：1超级管理员，2HR，3求职者
    @JsonProperty("iStatus")
    private Integer iStatus; //状态：1正常，0被注销
    @JsonProperty("sDirection")
    private String sDirection; //就业方向
    @JsonProperty("sDirectionName")
    private String sDirectionName; //就业方向名称
    @JsonProperty("sDescription")
    private String sDescription; //描述
    @JsonProperty("bChangePassword")
    private Boolean bChangePassword;
    @JsonProperty("dCreateTime")
    private Date dCreateTime; //创建时间
    @JsonProperty("dUpdateTime")
    private Date dUpdateTime; //更新时间

}
