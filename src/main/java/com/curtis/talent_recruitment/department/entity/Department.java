package com.curtis.talent_recruitment.department.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description: 部门实体类
 * @Date: Created in 11:06 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_department")
@ToString
public class Department {

    @JsonProperty("id")
    private String id; //主键UUID
    @JsonProperty("sDepartmentName")
    private String sDepartmentName; //部门名称
    @JsonProperty("sDescription")
    private String sDescription; //部门描述
    @JsonProperty("sCompanyID")
    private String sCompanyID; //所属公司
    @JsonProperty("sCompanyName")
    private String sCompanyName; //公司名称

}
