package com.silverhyuk.currencyconverter.web.dvo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CurrencyCodeVo {
    private int currencyCodeId;  /**<  통화코드 아이디  */
    private String currencyCode; /**<  통화코드 */
    private int currencyNumber;  /**<  통화코드 번호 */
    private String currencyName; /**<  통화코드 명 */
    private String regDatetime;  /**<  등록일 */
}
