package com.silverhyuk.currencyconverter.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilTest {

    @Test
    @DisplayName("UNIX 타임스템프를 사람이 볼 수 있게 변환 테스트")
    void unixTimestampToDateTime() throws Exception{
        //Given
        long timestamp = 1598805486;

        //When
        Date date = DateUtil.unixTimestampToDateTime(timestamp);
        String humanReadable = DateUtil.getDateStringFormat(DateUtil.FMT_DATETIME_SEP, date);

        // Then
        assertThat(date).isNotNull();
        assertThat(humanReadable).isNotNull();
    }
}