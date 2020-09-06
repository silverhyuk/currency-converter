package com.silverhyuk.currencyconverter.common.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
class CurrencyRepositoryTest {

    @Autowired
    CurrencyRepository currencyRepository;

    @Test
    public void getCurrencyRateSuccessTest() throws Exception{

        //given
        String targetCurrencyCode = "KRW";

        //when
        BigDecimal currencyRate = currencyRepository.getCurrencyRate(targetCurrencyCode);

        //then
        assertThat(currencyRate).isNotNull();
        assertThat(currencyRate).isNotNegative();
        assertThat(currencyRate).isBetween(new BigDecimal(0), new BigDecimal(10000));
    }

    @Test
    public void getCurrencyRateSuccessTest2() throws Exception{

        //given
        String targetCurrencyCode = "SILVERHYUK";
        BigDecimal decimal = new BigDecimal(10000);
        currencyRepository.setCurrencyRate(targetCurrencyCode, decimal);

        //when
        BigDecimal currencyRate = currencyRepository.getCurrencyRate(targetCurrencyCode);

        //then
        assertThat(currencyRate).isNotNull();
        assertThat(currencyRate).isEqualTo(decimal);
    }

    @Test
    public void getCurrencyRateExceptionTest() {

        //given
        String targetCurrencyCode = "KRW";

        //when
        try {
            currencyRepository.setCurrencyRate(targetCurrencyCode, null); // Null 입력시 Exception 발생해야 정상
            fail("테스트 실패");
        } catch (Exception e) {
            //then
            assertThat(e).hasMessage("잘못된 값을 입력했어요.");
        }
    }
}