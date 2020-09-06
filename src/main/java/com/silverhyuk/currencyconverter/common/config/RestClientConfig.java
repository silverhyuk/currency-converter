package com.silverhyuk.currencyconverter.common.config;

import com.silverhyuk.currencyconverter.common.properties.RestTemplateProperties;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *  [HTTP REST Client 를 만들기 위한 설정]
 */
@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final RestTemplateProperties restTemplateProperties;

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(restTemplateProperties.getFactory().getReadTimeout());
        factory.setConnectTimeout(restTemplateProperties.getFactory().getConnectTimeout());

        HttpClient httpClient = HttpClientBuilder.create()
                                                .setMaxConnTotal(restTemplateProperties.getHttpClient().getMaxConnTotal())
                                                .setMaxConnPerRoute(restTemplateProperties.getHttpClient().getMaxConnPerRoute())
                                                .build();

        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);

        return restTemplate;
    }
}
