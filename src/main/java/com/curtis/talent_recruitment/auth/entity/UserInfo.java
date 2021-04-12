package com.curtis.talent_recruitment.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description: JWT鉴权载荷对象
 * @Date: Created in 5:49 PM 3/9/2021
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @JsonProperty("id")
    private String id;
    @JsonProperty("sUsername")
    private String sUsername;
    @JsonProperty("isHR")
    private Boolean isHR;
    @JsonProperty("sAvatar")
    private String sAvatar;
    @JsonProperty("bRememberMe")
    private Boolean bRememberMe;
    @JsonProperty("iStatus")
    private Integer iStatus;
    @JsonProperty("sRealName")
    private String sRealName;

    public UserInfo(String id, String sUsername) {
        this.id = id;
        this.sUsername = sUsername;
    }

    public UserInfo(String id, String sUsername, Boolean isHR, String sAvatar, Boolean bRememberMe, Integer iStatus) {
        this.id = id;
        this.sUsername = sUsername;
        this.isHR = isHR;
        this.sAvatar = sAvatar;
        this.bRememberMe = bRememberMe;
        this.iStatus =iStatus;
    }
}
