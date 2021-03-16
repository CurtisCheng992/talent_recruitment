package com.curtis.talent_recruitment.utils.auth;

import org.apache.commons.lang3.StringUtils;

/**
 * 转换从 jwt 解析得到的 Object 类型的数据
 */
public class ObjectUtils {
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static Long toLong(Object obj) {
        if (obj == null) {
            return 0L;
        }
        if (obj instanceof Double || obj instanceof Float) {
            return Long.valueOf( StringUtils.substringBefore( obj.toString(), "." ) );
        }
        if (obj instanceof Number) {
            return Long.valueOf( obj.toString() );
        }
        if (obj instanceof String) {
            return Long.valueOf( obj.toString() );
        } else {
            return 0L;
        }
    }

    public static Integer toInt(Object obj) {
        return toLong( obj ).intValue();
    }

    public static Boolean toBoolean(Object o) {
        if (o == null) {
            return null;
        }
        return Boolean.parseBoolean( o.toString() );
    }
}
