package com.curtis.talent_recruitment.utils.user;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author Colin
 * @version 1.0.0
 * @description 后台参数校验工具类
 * @date 2020/10/29 17:02
 */
public class ParamCheckUtils {

    public static boolean checkPhone(String phone) {
        if (StringUtils.isBlank( phone )) {
            return false;
        }
        return Pattern.compile( "^1[3|4|5|7|8][0-9]{9}$" ).matcher( phone ).matches();
    }

    public static boolean checkEmail(String email) {
        if (StringUtils.isBlank( email )) {
            return false;
        }
        return Pattern.compile( "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$" ).matcher( email ).matches();
    }

}
