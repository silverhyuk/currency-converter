package com.silverhyuk.currencyconverter.common.scheduler;

import com.silverhyuk.currencyconverter.web.service.RestClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * [스케줄러 Task 셋팅]
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class ScheduleTask {


    private final RestClientService restClientService;

    /**
     * 매시간 0분마다 동작하는 크론
     */
    @Scheduled(cron = "0 0 * * * *")
    public void operateEveryHour() throws Exception {
        restClientService.runService();
        log.debug("Complete cron operation!");
    }
}
