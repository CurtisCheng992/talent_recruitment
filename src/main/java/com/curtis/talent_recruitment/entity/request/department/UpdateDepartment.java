package com.curtis.talent_recruitment.entity.request.department;

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

    private String sDepartmentName; //部门名称
    private String sDescription; //部门描述
    private String sCompanyID; //所属公司

}
