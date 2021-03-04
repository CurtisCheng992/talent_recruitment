package com.curtis.talent_recruitment.company.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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

    private String id; //主键UUID
    private String sCompanyName; //公司名称
    private String sCompanyLogo; //公司Logo
    private String sDescription; //公司描述
    private String sTelephone; //公司电话
    private String sAddress; //公司地址
    private Date dCreateTime; //创建时间
    private Date dUpdateTime; //更新时间

}
