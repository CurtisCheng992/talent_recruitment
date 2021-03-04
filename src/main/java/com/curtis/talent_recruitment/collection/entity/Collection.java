package com.curtis.talent_recruitment.collection.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description: 收藏实体类
 * @Date: Created in 11:33 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_collection")
@ToString
public class Collection {

    private String id; //主键UUID
    private String sUserID; //用户id
    private String sPositionID; //职位id
    private Date dCreateTime; //创建时间
    private Date dUpdateTime; //更新时间

}
