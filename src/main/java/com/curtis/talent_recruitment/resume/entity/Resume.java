package com.curtis.talent_recruitment.resume.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description: //简历实体类
 * @Date: Created in 10:59 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_resume")
@ToString
public class Resume {

    private String id; //主键UUID
    private String sUserID; //用户id
    private String sPicture; //照片
    private String sPersonalSummary; //个人总结
    private String sEducationExperience; //教育经历
    private String sAbility; //专业能力
    private String sWorkExperience; //实习/工作经历
    private String sCertificate; //获奖荣誉
    private String sJobDesire; //就业期望
    private String sSkillsAndOthers; //技能及其它
    private Date dCreateTime; //创建时间
    private Date dUpdateTime; //更新时间

}
