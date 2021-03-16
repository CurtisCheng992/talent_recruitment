package com.curtis.talent_recruitment.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Curtis
 * @Description: JWT鉴权载荷对象
 * @Date: Created in 5:49 PM 3/9/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String id;
    private String sUsername;
    private Boolean isHR;
    private String avatar;
    private Boolean rememberMe;
    private Integer status;

    public UserInfo(String id, String sUsername) {
        this.id = id;
        this.sUsername = sUsername;
    }

}
