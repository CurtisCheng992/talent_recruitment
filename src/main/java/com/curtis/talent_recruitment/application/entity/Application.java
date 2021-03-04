package com.curtis.talent_recruitment.application.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description: 申请实体类
 * @Date: Created in 11:32 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_application")
@ToString
public class Application {

    private String id; //主键UUID
    private String sResumeID; //简历id
    private String sPositionID; //职位id
    private String sHRID; //HRid
    private Integer iStatus; //状态：0申请未处理，1申请已处理
    private Date dCreateTime; //创建时间
    private Date dUpdateTime; //更新时间

}
