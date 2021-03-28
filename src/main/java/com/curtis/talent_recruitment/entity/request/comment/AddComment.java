package com.curtis.talent_recruitment.entity.request.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 8:14 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddComment {

    @JsonProperty("sContent")
    private String sContent; //评论内容
    @JsonProperty("sUserID")
    private String sUserID; //用户id
    @JsonProperty("sPositionID")
    private String sPositionID; //职位id
    @JsonProperty("sParentID")
    private String sParentID; //父级评论id

}
