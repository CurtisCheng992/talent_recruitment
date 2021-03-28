package com.curtis.talent_recruitment.position.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description: 职位实体类
 * @Date: Created in 11:02 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_position")
@ToString
public class Position {

    private String id; //主键UUID
    @JsonProperty("sPositionName")
    private String sPositionName; //职位名称
    @JsonProperty("sRequirement")
    private String sRequirement; //职位要求
    @JsonProperty("iQuantity")
    private Integer iQuantity; //招聘人数
    @JsonProperty("sWorkCity")
    private String sWorkCity; //工作城市
    @JsonProperty("sSalary")
    private String sSalary; //工资
    @JsonProperty("sWelfare")
    private String sWelfare; //福利待遇
    @JsonProperty("sCategoryID")
    private String sCategoryID; //分类id
    @JsonProperty("sDepartmentID")
    private String sDepartmentID; //部门id
    @JsonProperty("sCompanyID")
    private String sCompanyID; //公司id
    @JsonProperty("iViews")
    private Integer iViews; //已阅人数
    @JsonProperty("dCreateTime")
    private Date dCreateTime; //创建时间
    @JsonProperty("dUpdateTime")
    private Date dUpdateTime; //更新时间
    @JsonProperty("iHot")
    private Integer iHot; //热门值
    @JsonProperty("sIntroduction")
    private String sIntroduction;
    @JsonProperty("sEducation")
    private String sEducation;
    @JsonProperty("sCompanyName")
    private String sCompanyName;
    @JsonProperty("sCompanyType")
    private String sCompanyType;
    @JsonProperty("sCompanyDescription")
    private String sCompanyDescription;
    @JsonProperty("sCompanyLogo")
    private String sCompanyLogo;
    @JsonProperty("iCollections")
    private Integer iCollections;
    @JsonProperty("sDepartmentName")
    private String sDepartmentName;
    @JsonProperty("sDepartmentDesc")
    private String sDepartmentDesc;
    @JsonProperty("sHRID")
    private String sHRID;
    @JsonProperty("sCategoryName")
    private String sCategoryName;

}
