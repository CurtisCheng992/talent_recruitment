package com.curtis.talent_recruitment.entity.request.school;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("sCategoryName")
    private String sCategoryName;
    @JsonProperty("sDescription")
    private String sDescription;
    @JsonProperty("sParentID")
    private String sParentID;

}
