package com.curtis.talent_recruitment.entity.response.code;

/**
 * @author Curtis
 * @version 1.0.0
 * @description 响应代码接口
 *
 * 用户User: 10000
 * 通用Common: 20000
 * 分类Category: 30001
 * 公司Company: 40001
 * 部门Department: 50001
 * 职位Position: 60001
 * 简历Resume: 70001
 * 收藏Collection: 80001
 * 评论Comment: 90001
 * 学校School: 11001
 * 认证Auth: 12001
 * 申请Application: 13001
 *
 * @date 2021/2/23 10:20
 */
public interface ResponseCode {


    // 操作是否成功
    boolean success();

    // 响应代码
    int code();

    // 响应消息
    String message();

}
