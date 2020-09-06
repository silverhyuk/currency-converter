package com.silverhyuk.currencyconverter.common.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

/**
 *  Date 관련 유틸성 코드
 */
@Component
public final class DateUtil {

    public static final String FMT_DATE_YMD         = "yyyyMMdd";
    public static final String FMT_DATE_YMD_SEP     = "yyyy-MM-dd";
    public static final String FMT_DATETIME         = "yyyyMMddHHmmss";
    public static final String FMT_DATETIME_SEP     = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_DATETIME_Mil     = "yyyyMMddHHmmssSSS";
    public static final String FMT_DATETIME_Mil_SEP = "yyyy-MM-dd HH:mm:ss:SSS";

    /**
     * Unix 타임스템프를 Date 형으로 치환
     * @param unixTimeStamp
     * @return
     * @throws Exception
     */
    public static Date unixTimestampToDateTime(long unixTimeStamp) throws Exception {
        Date date = Date.from(Instant.ofEpochSecond(unixTimeStamp));
        return date;
    }

    /**
     * Date 를 사람이 볼수 있는 포멧으로 String 치환
     * @param dateFormat
     * @param date
     * @return
     * @throws Exception
     */
    public static String getDateStringFormat(String dateFormat , Date date) throws Exception{
        return (new SimpleDateFormat(dateFormat, Locale.KOREA)).format(date);
    }
}
