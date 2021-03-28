package com.curtis.talent_recruitment.entity.request.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:11 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateApplication {

    @JsonProperty("sResumeID")
    private String sResumeID;
    @JsonProperty("sPositionID")
    private String sPositionID;
    @JsonProperty("sPositionName")
    private String sPositionName; //职位名称
    @JsonProperty("sHRID")
    private String sHRID;
    @JsonProperty("sHRName")
    private String sHRName; //HR名称
    @JsonProperty("sHREmail")
    private String sHREmail; //HR的邮箱
    @JsonProperty("sUserID")
    private String sUserID;
    @JsonProperty("iStatus")
    private Integer iStatus;

}
