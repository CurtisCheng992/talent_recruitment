package com.curtis.talent_recruitment.entity.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 2:59 PM 4/10/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PasswordUser {

    @JsonProperty("sPassword")
    private String sPassword;
    @JsonProperty("sEmail")
    private String sEmail;
    @JsonProperty("sCode")
    private String sCode;

}
