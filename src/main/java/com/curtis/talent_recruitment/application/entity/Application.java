package com.curtis.talent_recruitment.application.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description: 申请实体类
 * @Date: Created in 11:32 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_application")
@ToString
public class Application {

    @JsonProperty("id")
    private String id; //主键UUID
    @JsonProperty("sResumeID")
    private String sResumeID; //简历id
    @JsonProperty("sPositionID")
    private String sPositionID; //职位id
    @JsonProperty("sPositionName")
    private String sPositionName; //职位名称
    @JsonProperty("sSalary")
    private String sSalary; //薪资
    @JsonProperty("sCompanyName")
    private String sCompanyName; //公司名称
    @JsonProperty("sWorkCity")
    private String sWorkCity; //工作城市
    @JsonProperty("sUserID")
    private String sUserID; //用户id
    @JsonProperty("sHRID")
    private String sHRID; //HRid
    @JsonProperty("sHRName")
    private String sHRName; //HR名称
    @JsonProperty("sHREmail")
    private String sHREmail; //HR的邮箱
    @JsonProperty("iStatus")
    private Integer iStatus; //状态：0申请未处理，1申请已处理
    @JsonProperty("dCreateTime")
    private Date dCreateTime; //创建时间
    @JsonProperty("dUpdateTime")
    private Date dUpdateTime; //更新时间
    @JsonProperty("sUserRealName")
    private String sUserRealName;
    @JsonProperty("sUserPhone")
    private String sUserPhone;
    @JsonProperty("sUserEmail")
    private String sUserEmail;
    @JsonProperty("iUserGender")
    private Integer iUserGender;
    @JsonProperty("sUserProvince")
    private String sUserProvince;
    @JsonProperty("sUserCity")
    private String sUserCity;
    @JsonProperty("iUserGraduationYear")
    private Integer iUserGraduationYear;
    @JsonProperty("sUserEducation")
    private String sUserEducation;
    @JsonProperty("sUserSchoolName")
    private String sUserSchoolName;

}
