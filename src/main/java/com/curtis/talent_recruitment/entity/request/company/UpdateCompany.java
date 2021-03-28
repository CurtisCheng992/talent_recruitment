package com.curtis.talent_recruitment.entity.request.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:41 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCompany {

    @JsonProperty("sCompanyName")
    private String sCompanyName;
    @JsonProperty("sCompanyLogo")
    private String sCompanyLogo;
    @JsonProperty("sCompanyType")
    private String sCompanyType;
    @JsonProperty("sDescription")
    private String sDescription;
    @JsonProperty("sTelephone")
    private String sTelephone;
    @JsonProperty("sAddress")
    private String sAddress;
    @JsonProperty("iHot")
    private Integer iHot; //热门值
    @JsonProperty("sIntroduction")
    private String sIntroduction; //宣传语
    @JsonProperty("sHRID")
    private String sHRID; //HRID
    @JsonProperty("iStatus")
    private String iStatus; //审核状态 0待审核 1审核通过

}
