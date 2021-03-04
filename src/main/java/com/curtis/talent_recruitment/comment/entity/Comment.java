package com.curtis.talent_recruitment.comment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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

    private String id; //主键UUID
    private String sContent; //评论内容
    private String sUserID; //用户id
    private String sPositionID; //职位id
    private String sParentID; //父级评论id
    private Date dCreateTime; //创建时间
    private Date dUpdateTime; //更新时间

}
