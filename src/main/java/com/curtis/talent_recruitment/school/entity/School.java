package com.curtis.talent_recruitment.school.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Author: Curtis
 * @Description: 学校实体类
 * @Date: Created in 3:50 PM 2/25/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
@ToString
public class School {

    private String id;
    @JsonProperty("sSchoolName")
    private String sSchoolName;

}
