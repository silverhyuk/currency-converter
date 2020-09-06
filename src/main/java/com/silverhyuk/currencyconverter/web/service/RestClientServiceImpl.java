package com.silverhyuk.currencyconverter.web.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.silverhyuk.currencyconverter.common.annotation.ProgressTime;
import com.silverhyuk.currencyconverter.common.properties.CurrencylayerProperties;
import com.silverhyuk.currencyconverter.common.repository.CurrencyRepository;
import com.silverhyuk.currencyconverter.common.utils.DateUtil;
import com.silverhyuk.currencyconverter.web.dvo.CurrencyApiHistoryVo;
import com.silverhyuk.currencyconverter.web.mapper.CurrencyApiHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * [HTTP 요청 서비스로직]
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class RestClientServiceImpl implements RestClientService {

    private final RestTemplate restTemplate;
    private final CurrencylayerProperties currencylayerProperties;
    private final CurrencyApiHistoryMapper currencyApiHistoryMapper;
    private final CurrencyRepository currencyRepository;

    /**
     * 화폐 API 호출 서비스 시작지점
     * @return
     */
    @ProgressTime
    @Override
    public String runService() throws Exception{
        String url = this.getRequestUrl();
        log.debug("sendUrl : {}", url);
        String  resultJsonStr = this.execRequest(url);

        JsonElement jsonParser = JsonParser.parseString(resultJsonStr);
        JsonObject jsonObject = jsonParser.getAsJsonObject();

        String isSuccess = jsonObject.get("success").toString();
        if(!isSuccess.equals("true")){
            throw new RuntimeException("API 응답으로 실패가 내려왔어요.");
        }
        log.debug("getSuccess : {}", isSuccess);

        setCurrencyRepository(resultJsonStr);
        setCurrencyHistory(resultJsonStr);

        return resultJsonStr;
    }

    /**
     * API로 받아온 환율 정보를 메모리에 적재
     * @param resultJsonStr
     * @throws Exception
     */
    private void setCurrencyRepository(String resultJsonStr) throws Exception {
        JsonElement jsonParser = JsonParser.parseString(resultJsonStr);
        JsonObject jsonObject = jsonParser.getAsJsonObject();
        JsonObject quotes = jsonObject.get("quotes").getAsJsonObject();


        log.debug("quotes : " + quotes.toString());
        log.debug("타입 : " + quotes.getClass().getName());

        String currencies = currencylayerProperties.getCurrencies();
        String source = currencylayerProperties.getSource();
        List<String> currencyCodeList = Arrays.asList(currencies.split(","));
        for(String currencyCode : currencyCodeList) {
            String currencyRate = quotes.get(source + currencyCode).toString();
            if (currencyRate != null && currencyRate.isEmpty() == false ) {
                BigDecimal currencyRateDecimal = new BigDecimal(currencyRate.trim());
                log.debug("setCurrencyRate [ {} : {} ]", currencyCode, currencyRate);
                currencyRepository.setCurrencyRate(currencyCode, currencyRateDecimal);
            }
        }
    }

    /**
     * DB에 history 기록
     * @param resultJsonStr
     */
    private void setCurrencyHistory(String resultJsonStr) {
        JsonElement jsonParser = JsonParser.parseString(resultJsonStr);
        JsonObject jsonObject = jsonParser.getAsJsonObject();

        CurrencyApiHistoryVo currencyApiHistoryVo = new CurrencyApiHistoryVo();
        try {
            Date date = DateUtil.unixTimestampToDateTime(jsonObject.get("timestamp").getAsLong());
            String dateStringFormat = DateUtil.getDateStringFormat(DateUtil.FMT_DATETIME_SEP, date);
            currencyApiHistoryVo.setCurrencyApiDatetime(dateStringFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        currencyApiHistoryVo.setExchangeRates(jsonObject.get("quotes").toString());
        int setResult = currencyApiHistoryMapper.setCurrencyApiHistoryInfo(currencyApiHistoryVo);

        if(setResult <= 0) {
            throw new IllegalArgumentException("쿼리 기록에 실패했어요.");
        }
    }

    /**
     * Request 실행
     * @param url
     * @return
     */
    @Override
    public String execRequest(String url) {

        String jsonStr = restTemplate.getForObject(url, String.class);
        log.debug("jsonStr : {}", jsonStr);
        return jsonStr;

    }

    /**
     * Request Url 조합
     * @return
     */
    private String getRequestUrl() {
        StringBuilder urlBuilder = new StringBuilder(currencylayerProperties.getUrl());
        urlBuilder.append("?access_key=");
        urlBuilder.append(currencylayerProperties.getAccessKey());
        urlBuilder.append("&source=");
        urlBuilder.append(currencylayerProperties.getSource());
        urlBuilder.append("&currencies=");
        urlBuilder.append(currencylayerProperties.getCurrencies());
        urlBuilder.append("&format=1");

        return urlBuilder.toString();
    }
}
