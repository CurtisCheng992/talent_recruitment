package com.curtis.talent_recruitment.department.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 10:11 AM 3/2/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_department")
@ToString
public class UserDepartment {

    @JsonProperty("id")
    private String id; //主键UUID
    @JsonProperty("sUserID")
    private String sUserID; //用户id
    @JsonProperty("sDepartmentID")
    private String sDepartmentID; //部门id

}
