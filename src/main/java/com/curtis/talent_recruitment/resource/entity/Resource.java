package com.curtis.talent_recruitment.resource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 12:47 PM 3/27/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_position")
@ToString
public class Resource {

    @JsonProperty("id")
    private String id; //主键id
    @JsonProperty("sUserID")
    private String sUserID;
    @JsonProperty("iType")
    private Integer iType; //文件类型 0目录 1文件
    @JsonProperty("sExt")
    private String sExt; //文件拓展名，如果类型为目录，则拓展名为空
    @JsonProperty("sName")
    private String sName; //文件名称
    @JsonProperty("sLocation")
    private String sLocation; //文件存储路径，若类型为目录，则存储路径为空
    @JsonProperty("sContentType")
    private String sContentType; //文件内容类型，若类型为目录，则内容类型为空
    @JsonProperty("dCreateTime")
    private Date dCreateTime; //创建时间
    @JsonProperty("dUpdateTime")
    private Date dUpdateTime; //更新时间

}
