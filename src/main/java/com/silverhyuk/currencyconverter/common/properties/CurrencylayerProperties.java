package com.silverhyuk.currencyconverter.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * application.yml
 * [apiCurrencylayer 프로퍼티]
 * type safe 하게 사용하기 위함
 *   url :
 *   accessKey :
 *   source :
 *   currencies :
 */
@Data
@Component
@ConfigurationProperties(prefix = "api-currencylayer")
public class CurrencylayerProperties {
    private String url;
    private String accessKey;
    private String source;
    private String currencies;
}
