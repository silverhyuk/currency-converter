package com.silverhyuk.currencyconverter.web.service;

import com.silverhyuk.currencyconverter.web.dvo.CurrencyApiHistoryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CurrencyServiceTest {
    @Autowired
    private CurrencyService currencyService;

    @Test
    public void getCurrencyHistoryListTest() throws Exception {
        //given

        //when
        List<CurrencyApiHistoryVo> currencyApiHistoryInfos = currencyService.getCurrencyApiHistoryList();

        //then
        assertThat(currencyApiHistoryInfos).isNotNull();
        assertThat(currencyApiHistoryInfos.size()).isGreaterThan(10);
        assertThat(currencyApiHistoryInfos.get(0).getCurrencyApiHistoryId()).isGreaterThan(0);
    }

    @Test
    public void getLastCurrencyHistoryTest() throws Exception {
        //given

        //when
        CurrencyApiHistoryVo lastCurrencyApiHistoryInfo = currencyService.getLastCurrencyApiHistoryInfo();

        //then
        assertThat(lastCurrencyApiHistoryInfo).isNotNull();
        assertThat(lastCurrencyApiHistoryInfo.getCurrencyApiHistoryId()).isGreaterThan(0);
    }

    @Test
    public void setCurrencyCodeNameToMemoryTest() throws Exception {
        //given

        //when
        boolean toMemory = currencyService.setCurrencyCodeNameToMemory();

        //then
        assertThat(toMemory).isTrue();
    }
}