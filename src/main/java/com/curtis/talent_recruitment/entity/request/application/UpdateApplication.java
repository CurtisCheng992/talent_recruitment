package com.curtis.talent_recruitment.entity.request.application;

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

    private String sResumeID;
    private String sPositionID;
    private String sHRID;
    private Integer iStatus;

}
