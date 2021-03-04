package com.curtis.talent_recruitment.entity.response.code;

/**
 * @author Curtis
 * @version 1.0.0
 * @description 响应代码接口
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
