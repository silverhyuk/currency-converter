package com.silverhyuk.currencyconverter.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * application.yml
 * [rest-template] 프로퍼티
 *   type safe 하게 사용하기 위함
 *   factory:
 *     readTimeout:
 *     connectTimeout:
 *   httpClient:
 *     maxConnTotal:
 *     maxConnPerRoute:
 */
@Data
@Component
@ConfigurationProperties(prefix = "rest-template")
public class RestTemplateProperties {

    private Factory factory = new Factory();
    private HttpClient httpClient = new HttpClient();

    @Data
    public static class Factory {
        private int readTimeout;
        private int connectTimeout;
    }

    @Data
    public static class HttpClient {
        private int maxConnTotal;
        private int maxConnPerRoute;
    }
}

