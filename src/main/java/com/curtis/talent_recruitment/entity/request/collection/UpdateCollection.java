package com.curtis.talent_recruitment.entity.request.collection;

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

    private String sUserID; //用户id
    private String sPositionID; //职位id

}
