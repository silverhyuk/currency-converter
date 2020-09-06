package com.silverhyuk.currencyconverter.common.repository;


import com.silverhyuk.currencyconverter.common.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * [환율 정보 저장소]
 * 이곳에서 메모리로 담아 두고 웹에서 요청에 있을때마다 저장소에 있는 내용을 전송한다.
 * 주기적으로 해당 메모리의 내용은 업데이트 된다.
 */
@Log4j2
@Repository
public class CurrencyRepository {

    private Map<String, BigDecimal> currencyRateRepo;
    private Map<String, String> currencyCodeRepo;

    /**
     * Constructor
     */
    public CurrencyRepository() {
        this.currencyRateRepo = new HashMap<>();
        this.currencyCodeRepo = new HashMap<>();
    }

    /**
     * 환율정보 저장소에서  꺼내기
     * @param targetCurrencyCode 타겟 국가 화폐 코드
     * @return
     */
    public BigDecimal getCurrencyRate(String targetCurrencyCode) throws Exception{
        if(currencyRateRepo == null || currencyRateRepo.isEmpty() || currencyRateRepo.size() <= 0) {
            log.error("환율 정보 저장소에 아무런 값이 없습니다.");
            throw new NotFoundException("환율 정보 저장소에 아무런 값이 없습니다.");
        }

        BigDecimal currencyRate = currencyRateRepo.get(targetCurrencyCode);

        if(currencyRate == null) {
            log.error("[{}] 해당 코드에 대한 값이 존재하지 않습니다.", targetCurrencyCode);
            throw new NotFoundException("[" + targetCurrencyCode + "] 해당 코드에 대한 값이 존재하지 않습니다.");
        }

        return currencyRate;
    }

    /**
     * 환율정보 저장소에 입력
     * @param currencyCode 국가 화폐 코드
     * @param currencyRate 비율
     */
    public void setCurrencyRate(String currencyCode, BigDecimal currencyRate) throws Exception{
        if(currencyCode == null || currencyRate == null) {
            log.error("[{}:{}] 잘못된 값을 입력했어요.", currencyCode, currencyRate);
            throw new IllegalArgumentException("잘못된 값을 입력했어요.");
        }

        currencyRateRepo.put(currencyCode, currencyRate);
        log.debug("환율정보 저장소 갯수 : {}", currencyRateRepo.size());
    }


    /**
     * 환율코드명 저장소에서  꺼내기
     * @param targetCurrencyCode 타겟 국가 화폐 코드
     * @return
     */
    public String getCurrencyCodeName(String targetCurrencyCode) throws Exception{
        if(currencyCodeRepo == null || currencyCodeRepo.isEmpty() || currencyCodeRepo.size() <= 0) {
            log.error("환율코드명 저장소에 아무런 값이 없습니다.");
            throw new NotFoundException("환율코드명 저장소에 아무런 값이 없습니다.");
        }

        String currencyName = currencyCodeRepo.get(targetCurrencyCode);

        if(currencyName == null) {
            log.error("[{}] 해당 코드에 대한 값이 존재하지 않습니다.", targetCurrencyCode);
            throw new NullPointerException("[" + targetCurrencyCode + "] 해당 코드에 대한 값이 존재하지 않습니다.");
        }

        return currencyName;
    }
    /**
     * 환율코드이름 저장소에 입력
     *
     */
    public void setCurrencyCode(String currencyCode, String currencyName) throws Exception{
        if(currencyCode == null || currencyName == null) {
            log.error("[{}:{}] 잘못된 값을 입력했어요.", currencyCode, currencyName);
            throw new IllegalArgumentException("잘못된 값을 입력했어요.");
        }

        this.currencyCodeRepo.put(currencyCode, currencyName);
        log.debug("[{}:{}]입력성공 / 환율코드이름 저장소 갯수 : {}", currencyCode, currencyName, currencyCodeRepo.size());
    }


}
