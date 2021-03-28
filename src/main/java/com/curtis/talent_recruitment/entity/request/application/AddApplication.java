package com.curtis.talent_recruitment.entity.request.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:10 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddApplication {

    @JsonProperty("sResumeID")
    private String sResumeID;
    @JsonProperty("sPositionID")
    private String sPositionID;
    @JsonProperty("sHRID")
    private String sHRID;
    @JsonProperty("sUserID")
    private String sUserID;
    @JsonProperty("iStatus")
    private Integer iStatus;

}
