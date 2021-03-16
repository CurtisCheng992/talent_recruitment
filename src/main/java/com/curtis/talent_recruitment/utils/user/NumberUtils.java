package com.curtis.talent_recruitment.utils.user;

import java.util.Random;

/**
 * @author Colin
 * @version 1.0.0
 * @description 数字工具类
 * @date 2020/10/25 16:20
 */
public class NumberUtils {

    /**
     * 生成指定位数的随机数字
     */
    public static String generateCode(int len) {
        len = Math.min( len, 8 );
        int min = Double.valueOf( Math.pow( 10, len - 1 ) ).intValue();
        int num = new Random().nextInt( Double.valueOf( Math.pow( 10, len + 1 ) ).intValue() - 1 ) + min;
        return String.valueOf( num ).substring( 0, len );
    }
}
