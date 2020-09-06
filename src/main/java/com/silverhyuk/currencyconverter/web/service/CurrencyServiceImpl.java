package com.silverhyuk.currencyconverter.web.service;

import com.silverhyuk.currencyconverter.common.annotation.ProgressTime;
import com.silverhyuk.currencyconverter.common.repository.CurrencyRepository;
import com.silverhyuk.currencyconverter.web.dvo.CurrencyApiHistoryVo;
import com.silverhyuk.currencyconverter.web.dvo.CurrencyCodeVo;
import com.silverhyuk.currencyconverter.web.mapper.CurrencyApiHistoryMapper;
import com.silverhyuk.currencyconverter.web.mapper.CurrencyCodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * [환율정보 서비스 로직]
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyApiHistoryMapper currencyApiHistoryMapper;
    private final CurrencyCodeMapper currencyCodeMapper;
    private final CurrencyRepository repository;

    /**
     * DB에 쌓여있는 환율 API 적재 리스트 기록 호출
     * @return
     */
    @ProgressTime
    @Override
    public List<CurrencyApiHistoryVo> getCurrencyApiHistoryList() {
        return currencyApiHistoryMapper.getCurrencyApiHistoryList(new CurrencyApiHistoryVo());
    }

    /**
     * DB에 쌓여있는 환율 API 적재된 최종 기록 호출
     * @return
     */
    @ProgressTime
    @Override
    public CurrencyApiHistoryVo getLastCurrencyApiHistoryInfo() {
        CurrencyApiHistoryVo param = new CurrencyApiHistoryVo();
        param.setLastData(true);
        List<CurrencyApiHistoryVo> currencyApiHistoryInfos = currencyApiHistoryMapper.getCurrencyApiHistoryList(param);
        return currencyApiHistoryInfos.get(0);
    }

    /**
     * 환율 국가 코드 리스트 호출
     * @return
     */
    @ProgressTime
    @Override
    public List<CurrencyCodeVo> getCurrencyCodeList() {
        return currencyCodeMapper.getCurrencyCodeList(new CurrencyCodeVo());
    }

    /**
     * 환율 코드를 메모리에 적재
     * (프로그램 시작 시점)
     * @return
     * @throws Exception
     */
    @ProgressTime
    @Override
    public boolean setCurrencyCodeNameToMemory() throws Exception{
        List<CurrencyCodeVo> currencyCodeList = getCurrencyCodeList();
        int totalCount = currencyCodeList.size();
        int cnt = 0;
        for(CurrencyCodeVo currencyCodeVo : currencyCodeList) {
            String currencyCode = currencyCodeVo.getCurrencyCode();
            String currencyName = currencyCodeVo.getCurrencyName();
            try {
                repository.setCurrencyCode(currencyCode, currencyName);
            } catch (Exception e) {
                log.error("입력 실패 [{}:{}] - {}", currencyCode, currencyName, e.getMessage() );
                throw new RuntimeException("입력 실패 ["+currencyCode+":"+currencyName+"] - "+ e.getMessage());
            }
            cnt++;
        }

        if(totalCount != cnt) {
            log.error("입력 갯수 확인 : {}/{}",totalCount,cnt);
            return false;
        }
        return true;

    }

    /**
     * 메모리에서 환율코드를 통해 환율코드명 조회
     * KRW -> 대한민국
     * @param currencyCode
     * @return
     * @throws Exception
     */
    @ProgressTime
    @Override
    public String getCurrencyCodeNameFromMemory(String currencyCode) throws Exception {
        return  repository.getCurrencyCodeName(currencyCode);
    }

    /**
     * 메모리에서 환율코드를 통해 환율 조회
     * @param currencyCode
     * @return
     * @throws Exception
     */
    @ProgressTime
    @Override
    public BigDecimal getCurrencyRateFromMemory(String currencyCode) throws Exception {
        return repository.getCurrencyRate(currencyCode);
    }


}
