package com.curtis.talent_recruitment.entity.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:52 PM 3/29/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AvatarUser {

    @JsonProperty("realName")
    private String realName; // 真实姓名
    @JsonProperty("sAvatar")
    private String sAvatar;

}
