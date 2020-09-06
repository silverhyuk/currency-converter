package com.silverhyuk.currencyconverter.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyDto {
    private String sourceName;         // 기준국가명
    private String sourceCode;         // 기준국가코드
    private String targetCurrencyCode; // 환율대상코드
    private String currencyRate;       // 환율
    private BigDecimal remittance;     // 송금액
    private String amountReceived;     // 수취금액
}
