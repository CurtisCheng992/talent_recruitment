package com.curtis.talent_recruitment.entity.request.position;

import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:19 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdatePosition {

    private String sPositionName; //职位名称
    private String sRequirement; //职位要求
    private Integer iQuantity; //招聘人数
    private String sWorkCity; //工作城市
    private String sSalary; //工资
    private String sWelfare; //福利待遇
    private String sCategoryID; //分类id
    private String sDepartmentID; //部门id
    private Integer iViews; //已阅人数

}
