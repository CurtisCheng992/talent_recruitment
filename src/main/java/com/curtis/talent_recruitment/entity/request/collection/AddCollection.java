package com.curtis.talent_recruitment.entity.request.collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:43 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddCollection {

    @JsonProperty("sUserID")
    private String sUserID; //用户id
    @JsonProperty("sPositionID")
    private String sPositionID; //职位id

}
