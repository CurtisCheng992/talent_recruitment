package com.curtis.talent_recruitment.category.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @Author: Curtis
 * @Description: 分类实体类
 * @Date: Created in 11:10 AM 2/23/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_category")
@ToString
public class Category {

    private String id; //主键UUID
    private String sCategoryName; //分类名称
    private String sDescription; //分类描述
    private String sParentID; //父级分类ID

}
