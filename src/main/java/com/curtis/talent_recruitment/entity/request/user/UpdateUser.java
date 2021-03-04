package com.curtis.talent_recruitment.entity.request.user;

import lombok.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 2:42 PM 2/25/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateUser {

    private String sUsername; //用户名
    private String sPassword; //密码
    private String sRealName; //真实姓名
    private String sPhone; //电话号码
    private String sEmail; //电子邮箱
    private Integer iGender; //性别
    private Integer iAge; //年龄
    private String sAvatar; //头像
    private String sProvince; //省份
    private String sCity; //城市
    private Integer iGraduationYear; //毕业年份
    private String sMajor; //专业
    private String sEducation; //学历
    private String sSchoolID; //学校id
    private String sDirection; //就业方向
    private String sDescription; //描述
    private Integer iStatus; //状态：1正常，2被注销

}
