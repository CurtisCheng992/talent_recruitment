package com.curtis.talent_recruitment.entity.request.company;

import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:39 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddCompany {

    private String sCompanyName;
    private String sCompanyLogo;
    private String sDescription;
    private String sTelephone;
    private String sAddress;

}
