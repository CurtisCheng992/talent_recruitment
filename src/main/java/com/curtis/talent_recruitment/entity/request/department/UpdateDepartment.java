package com.curtis.talent_recruitment.entity.request.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:37 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateDepartment {

    @JsonProperty("sDepartmentName")
    private String sDepartmentName; //部门名称
    @JsonProperty("sDescription")
    private String sDescription; //部门描述
    @JsonProperty("sCompanyID")
    private String sCompanyID; //所属公司

}
