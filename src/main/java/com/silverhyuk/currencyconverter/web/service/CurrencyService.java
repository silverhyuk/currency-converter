package com.silverhyuk.currencyconverter.web.service;

import com.silverhyuk.currencyconverter.web.dvo.CurrencyApiHistoryVo;
import com.silverhyuk.currencyconverter.web.dvo.CurrencyCodeVo;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyService {
    List<CurrencyApiHistoryVo> getCurrencyApiHistoryList();
    CurrencyApiHistoryVo getLastCurrencyApiHistoryInfo();
    List<CurrencyCodeVo> getCurrencyCodeList();
    boolean setCurrencyCodeNameToMemory() throws Exception;
    String getCurrencyCodeNameFromMemory(String currencyCode) throws Exception;
    BigDecimal getCurrencyRateFromMemory(String currencyCode) throws Exception;
}
