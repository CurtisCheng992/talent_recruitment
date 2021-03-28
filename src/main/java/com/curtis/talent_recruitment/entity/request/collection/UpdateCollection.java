package com.curtis.talent_recruitment.entity.request.collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:44 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCollection {

    @JsonProperty("sUserID")
    private String sUserID; //用户id
    @JsonProperty("sPositionID")
    private String sPositionID; //职位id
    @JsonProperty("sPositionName")
    private String sPositionName; //职位名称
    @JsonProperty("sSalary")
    private String sSalary; //薪资
    @JsonProperty("sWorkCity")
    private String sWorkCity; //工作城市

}
