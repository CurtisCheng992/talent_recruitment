package com.curtis.talent_recruitment.entity.request.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:28 PM 3/29/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PictureResume {

    @JsonProperty("sPicture")
    private String sPicture;

}
