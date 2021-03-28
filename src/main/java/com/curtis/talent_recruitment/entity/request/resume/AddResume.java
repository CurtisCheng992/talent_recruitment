package com.curtis.talent_recruitment.entity.request.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:59 PM 3/1/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddResume {

    @JsonProperty("sUserID")
    private String sUserID; //用户id
    @JsonProperty("sPicture")
    private String sPicture; //照片
    @JsonProperty("sPersonalSummary")
    private String sPersonalSummary; //个人总结
    @JsonProperty("sEducationExperience")
    private String sEducationExperience; //教育经历
    @JsonProperty("sAbility")
    private String sAbility; //专业能力
    @JsonProperty("sWorkExperience")
    private String sWorkExperience; //实习/工作经历
    @JsonProperty("sCertificate")
    private String sCertificate; //获奖荣誉
    @JsonProperty("sJobDesire")
    private String sJobDesire; //就业期望
    @JsonProperty("sSkillsAndOthers")
    private String sSkillsAndOthers; //技能及其它

}
