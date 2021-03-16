package com.curtis.talent_recruitment.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @Author: Curtis
 * @Description: 用户实体类
 * @Date: Created in 10:27 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
@ToString
public class User {

    private String id; //主键UUID
    private String sUsername; //用户名
    private String sPassword; //密码
    private String sRealName; //真实姓名
    private String sPhone; //电话号码
    private String sEmail; //电子邮箱
    private Integer iGender; //性别：0女 1男
    private Integer iAge; //年龄
    private String sAvatar; //头像
    private String sProvince; //省份
    private String sCity; //城市
    private Integer iGraduationYear; //毕业年份
    private String sMajor; //专业
    private String sEducation; //学历
    private String sSchoolID; //学校id
    private Integer iRoleType; //角色类型：1超级管理员，2HR，3求职者
    private Integer iStatus; //状态：1正常，0被注销
    private String sDirection; //就业方向
    private String sDescription; //描述
    private Date dCreateTime; //创建时间
    private Date dUpdateTime; //更新时间

}
