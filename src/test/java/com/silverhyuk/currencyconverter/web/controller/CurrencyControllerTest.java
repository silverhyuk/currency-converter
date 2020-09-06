package com.silverhyuk.currencyconverter.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.silverhyuk.currencyconverter.web.dto.CurrencyDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("환율 정보 기록 가져오는 성공 테스트")
    public void getCurrencyInfoSuccessTest() throws Exception {
        mockMvc.perform(get("/currency/infos"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("원하는 환율 정보를 가져오는 성공 테스트")
    public void getCurrencyRateSuccessTest() throws Exception {
        mockMvc.perform(get("/currency/getCurrencyRate")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .param("targetCurrencyCode", "KRW"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("원하는 환율 정보를 가져오는 실패 테스트")
    public void getCurrencyRateFailTest() throws Exception {

        mockMvc.perform(get("/currency/getCurrencyRate")
                    .contentType(MediaType.TEXT_PLAIN_VALUE)
                    .param("targetCurrencyCode", "KR"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("None")));
    }

    @Test
    @DisplayName("수취금액을 계산하여 가져오는 성공 테스트")
    public void getAmountReceivedSuccessTest() throws Exception {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setTargetCurrencyCode("KRW");
        currencyDto.setRemittance(new BigDecimal(100));
        Gson gson = new Gson();
        String toJson = gson.toJson(currencyDto);

        mockMvc.perform(post("/currency/getAmountReceived")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("KRW")))
                .andExpect(content().string(containsString("100")))
                .andExpect(content().string(containsString("amountReceived")));
    }

    @Test
    @DisplayName("Request 값 오류로 수취금액을 계산하여 가져오는 실패 테스트")
    public void getAmountReceivedFailTest() throws Exception {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setTargetCurrencyCode("KRW");

        Gson gson = new Gson();
        String toJson = gson.toJson(currencyDto);

        mockMvc.perform(post("/currency/getAmountReceived")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}