package com.curtis.talent_recruitment.resume.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description: //简历实体类
 * @Date: Created in 10:59 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_resume")
@ToString
public class Resume {

    private String id; //主键UUID
    @JsonProperty("sUserID")
    private String sUserID; //用户id
    @JsonProperty("sPicture")
    private String sPicture; //照片
    @JsonProperty("sPersonalSummary")
    private String sPersonalSummary; //个人总结
    @JsonProperty("sEducationExperience")
    private String sEducationExperience; //教育经历
    @JsonProperty("sAbility")
    private String sAbility; //专业能力
    @JsonProperty("sWorkExperience")
    private String sWorkExperience; //实习/工作经历
    @JsonProperty("sCertificate")
    private String sCertificate; //获奖荣誉
    @JsonProperty("sJobDesire")
    private String sJobDesire; //就业期望
    @JsonProperty("sSkillsAndOthers")
    private String sSkillsAndOthers; //技能及其它
    @JsonProperty("dCreateTime")
    private Date dCreateTime; //创建时间
    @JsonProperty("dUpdateTime")
    private Date dUpdateTime; //更新时间
    @JsonProperty("sRealName")
    private String sRealName;
    @JsonProperty("sPhone")
    private String sPhone;
    @JsonProperty("sEmail")
    private String sEmail;
    @JsonProperty("iGender")
    private Integer iGender;
    @JsonProperty("iAge")
    private Integer iAge;
    @JsonProperty("sProvince")
    private String sProvince;
    @JsonProperty("sCity")
    private String sCity;
    @JsonProperty("iGraduationYear")
    private Integer iGraduationYear;
    @JsonProperty("sMajor")
    private String sMajor;
    @JsonProperty("sEducation")
    private String sEducation;

}
