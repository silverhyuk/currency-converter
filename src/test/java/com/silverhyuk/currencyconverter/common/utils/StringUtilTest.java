package com.silverhyuk.currencyconverter.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilTest {

    @Test
    @DisplayName("Null String 테스트")
    public void isNullStringTest() {

        //Given
        String nullStr = null;

        //When
        boolean result = StringUtil.isEmptyString(nullStr);

        //Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("비어있는 String 테스트")
    public void isEmptyStringTest() {

        //Given
        String emptyStr = "";

        //When
        boolean result = StringUtil.isEmptyString(emptyStr);

        //Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("소숫점 2째 반올림 / 숫자 3자리 comma 형변환 테스트")
    public void decimalFormatterTest() {

        //Given
        BigDecimal exNumber = new BigDecimal("12345678901234567890.1234567890");

        //When
        String formattedStr = StringUtil.decimalFormatter(exNumber, StringUtil.FMT_GLOBAL_MONEY);

        //Then
        assertThat(formattedStr).isEqualTo("12,345,678,901,234,567,890.12");
    }
}