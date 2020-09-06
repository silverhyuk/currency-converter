package com.silverhyuk.currencyconverter.common.utils;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

/**
 *   String 관련 유틸성 코드
 */
public class StringUtil {


    public static final String FMT_GLOBAL_MONEY = "###,###.##";

    /**
     * String 이 비었는지 체크
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 숫자 포멧 변경
     * @param object
     * @param pattern
     * @return
     */
    public static String decimalFormatter(Object object, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(object);
    }
}
