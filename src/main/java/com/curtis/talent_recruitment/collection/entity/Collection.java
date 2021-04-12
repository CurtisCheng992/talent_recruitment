package com.curtis.talent_recruitment.collection.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description: 收藏实体类
 * @Date: Created in 11:33 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_collection")
@ToString
public class Collection {

    @JsonProperty("id")
    private String id; //主键UUID
    @JsonProperty("sUserID")
    private String sUserID; //用户id
    @JsonProperty("sPositionID")
    private String sPositionID; //职位id
    @JsonProperty("sPositionName")
    private String sPositionName; //职位名称
    @JsonProperty("sSalary")
    private String sSalary; //薪资
    @JsonProperty("sWorkCity")
    private String sWorkCity; //工作城市
    @JsonProperty("dCreateTime")
    private Date dCreateTime; //创建时间
    @JsonProperty("dUpdateTime")
    private Date dUpdateTime; //更新时间
    @JsonProperty("iQuantity")
    private Integer iQuantity; //招聘人数
    @JsonProperty("sCompanyName")
    private String sCompanyName;
    @JsonProperty("sRequirement")
    private String sRequirement;
    @JsonProperty("sUsername")
    private String sUsername;


}
