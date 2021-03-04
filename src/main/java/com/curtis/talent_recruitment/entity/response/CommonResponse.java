package com.curtis.talent_recruitment.entity.response;

import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Curtis
 * @version 1.0.0
 * @description 通用的响应结果类
 * @date 2020/10/23 15:27
 */
@Data
@NoArgsConstructor
public class CommonResponse {
    private static final boolean SUCCESS = true;
    private static final int CODE = 20000;

    // 操作是否成功
    private boolean success = SUCCESS;

    // 响应代码
    private int code = CODE;

    // 响应消息
    private String message;

    public CommonResponse(ResponseCode responseCode) {
        this.success = responseCode.success();
        this.code = responseCode.code();
        this.message = responseCode.message();
    }

    public static CommonResponse SUCCESS() {
        return new CommonResponse(CommonCode.SUCCESS);
    }

    public static CommonResponse FAIL() {
        return new CommonResponse(CommonCode.FAIL);
    }

    public static CommonResponse SERVER_ERROR() {
        return new CommonResponse(CommonCode.SERVER_ERROR);
    }
}
