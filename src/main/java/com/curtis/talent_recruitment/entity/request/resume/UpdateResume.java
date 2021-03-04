package com.curtis.talent_recruitment.entity.request.resume;

import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 6:00 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateResume {

    private String sUserID; //用户id
    private String sPicture; //照片
    private String sPersonalSummary; //个人总结
    private String sEducationExperience; //教育经历
    private String sAbility; //专业能力
    private String sWorkExperience; //实习/工作经历
    private String sCertificate; //获奖荣誉
    private String sJobDesire; //就业期望
    private String sSkillsAndOthers; //技能及其它

}
