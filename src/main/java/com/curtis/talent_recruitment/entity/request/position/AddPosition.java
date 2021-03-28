package com.curtis.talent_recruitment.entity.request.position;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:18 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddPosition {

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
    @JsonProperty("iHot")
    private Integer iHot; //热门值
    @JsonProperty("sEducation")
    private String sEducation;
    @JsonProperty("sHRID")
    private String sHRID;
    @JsonProperty("sCategoryName")
    private String sCategoryName;
    @JsonProperty("sDepartmentName")
    private String sDepartmentName;

}
