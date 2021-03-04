package com.curtis.talent_recruitment.position.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
    private String sPositionName; //职位名称
    private String sRequirement; //职位要求
    private Integer iQuantity; //招聘人数
    private String sWorkCity; //工作城市
    private String sSalary; //工资
    private String sWelfare; //福利待遇
    private String sCategoryID; //分类id
    private String sDepartmentID; //部门id
    private Integer iViews; //已阅人数
    private Date dCreateTime; //创建时间
    private Date dUpdateTime; //更新时间

}
