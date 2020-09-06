package com.silverhyuk.currencyconverter.common.config;

import com.silverhyuk.currencyconverter.web.service.CurrencyService;
import com.silverhyuk.currencyconverter.web.service.RestClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * [프로그램 시작시 동작]
 *
 * restClientService 동작 -> live 환율정보 api 호출하여 메모리 적재
 * currencyService 동작 -> DB에 있는 Currency Code 정보 메모리에 적재
 */
@Component
@RequiredArgsConstructor
public class ApplicationStartConfig implements ApplicationRunner {

    private final RestClientService restClientService;
    private final CurrencyService currencyService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        restClientService.runService();
        currencyService.setCurrencyCodeNameToMemory();
    }
}
