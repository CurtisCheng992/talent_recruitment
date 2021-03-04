package com.curtis.talent_recruitment.entity.request.comment;

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

    private String sContent; //评论内容
    private String sUserID; //用户id
    private String sPositionID; //职位id
    private String sParentID; //父级评论id

}
