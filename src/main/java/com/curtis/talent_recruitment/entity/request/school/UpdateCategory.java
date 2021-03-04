package com.curtis.talent_recruitment.entity.request.school;

import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:16 PM 2/25/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCategory {

    private String sCategoryName;
    private String sDescription;
    private String sParentID;

}
