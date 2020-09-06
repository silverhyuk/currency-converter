package com.silverhyuk.currencyconverter.web.dvo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CurrencyApiHistoryVo {
    private int currencyApiHistoryId;    /**< 통화정보 기록 아이디 */
    private String currencyApiDatetime;  /**< 통화 api 응답 데이터 시간 */
    private String exchangeRates;        /**< 환율정보(Json) */
    private String regDatetime;          /**< 기록시간 */
    private boolean isLastData = false;  /** 마지막 값 처리 여부*/
}
