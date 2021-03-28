package com.curtis.talent_recruitment.company.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description: 公司实体类
 * @Date: Created in 11:08 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_company")
@ToString
public class Company {

    @JsonProperty("id")
    private String id; //主键UUID
    @JsonProperty("sCompanyName")
    private String sCompanyName; //公司名称
    @JsonProperty("sCompanyLogo")
    private String sCompanyLogo; //公司Logo
    @JsonProperty("sCompanyType")
    private String sCompanyType; // 公司类型
    @JsonProperty("sDescription")
    private String sDescription; //公司描述
    @JsonProperty("sTelephone")
    private String sTelephone; //公司电话
    @JsonProperty("sAddress")
    private String sAddress; //公司地址
    @JsonProperty("iHot")
    private Integer iHot; //热门值
    @JsonProperty("iTotalQuantity")
    private Integer iTotalQuantity; //总共招聘的职位数
    @JsonProperty("sIntroduction")
    private String sIntroduction; //宣传语
    @JsonProperty("sHRID")
    private String sHRID; //HRID
    @JsonProperty("iStatus")
    private String iStatus; //审核状态 0待审核 1审核通过
    @JsonProperty("dCreateTime")
    private Date dCreateTime; //创建时间
    @JsonProperty("dUpdateTime")
    private Date dUpdateTime; //更新时间

}
