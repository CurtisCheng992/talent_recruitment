package com.curtis.talent_recruitment.comment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description: 评论实体类
 * @Date: Created in 11:35 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_comment")
@ToString
public class Comment {

    @JsonProperty("id")
    private String id; //主键UUID
    @JsonProperty("sContent")
    private String sContent; //评论内容
    @JsonProperty("sUserID")
    private String sUserID; //用户id
    @JsonProperty("sPositionID")
    private String sPositionID; //职位id
    @JsonProperty("sParentID")
    private String sParentID; //父级评论id
    @JsonProperty("dCreateTime")
    private Date dCreateTime; //创建时间
    @JsonProperty("dUpdateTime")
    private Date dUpdateTime; //更新时间

}
