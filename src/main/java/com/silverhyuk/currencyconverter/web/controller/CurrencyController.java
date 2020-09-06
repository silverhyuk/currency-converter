package com.silverhyuk.currencyconverter.web.controller;

import com.silverhyuk.currencyconverter.common.properties.CurrencylayerProperties;
import com.silverhyuk.currencyconverter.common.utils.StringUtil;
import com.silverhyuk.currencyconverter.web.dto.CurrencyDto;
import com.silverhyuk.currencyconverter.web.dvo.CurrencyApiHistoryVo;
import com.silverhyuk.currencyconverter.web.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final CurrencylayerProperties currencylayerProperties;


    /**
     * 메인화면
     * /currency/main
     *
     * @param mav
     * @return
     * @throws Exception
     */
    @GetMapping("/main")
    public ModelAndView main(ModelAndView mav) throws Exception {
        mav.setViewName("main");
        Map<String, String> selectCurrencies = getCurrencies();

        CurrencyDto currencyDto = getSourceCodeName();

        mav.addObject("selectCurrencies", selectCurrencies);
        mav.addObject("currencyDto", currencyDto);

        return mav;
    }

    /**
     * 환율 기록 정보 제공
     * /currency/infos
     *
     * @param currencyDto
     * @param errors
     * @return
     */
    @GetMapping(value = "/infos")
    public ResponseEntity getCurrencyInfos(CurrencyDto currencyDto, Errors errors){
        List<CurrencyApiHistoryVo> apiHistoryInfos = currencyService.getCurrencyApiHistoryList();
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok(apiHistoryInfos);
    }

    /**
     * 환율 정보 제공
     * /currency/getCurrencyRate?targetCurrencyCode=KRW
     *
     * @param currencyDto
     * @param errors
     * @return
     */
    @GetMapping(value = "/getCurrencyRate")
    public ResponseEntity getCurrencyRate(CurrencyDto currencyDto, Errors errors) {
        if(errors.hasErrors() || currencyDto.getTargetCurrencyCode() == null) {
            return ResponseEntity.badRequest().body(errors);
        }
        log.debug("targetCurrencyCode : {}", currencyDto.getTargetCurrencyCode());

        BigDecimal currencyRate = null;
        try {
            currencyRate = currencyService.getCurrencyRateFromMemory(currencyDto.getTargetCurrencyCode());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok("None");
        }
        if(currencyRate == null || currencyRate.equals("")){
            return ResponseEntity.ok("None");
        }

        String formattedRate = StringUtil.decimalFormatter(currencyRate, StringUtil.FMT_GLOBAL_MONEY);
        currencyDto.setCurrencyRate(formattedRate);

        return ResponseEntity.ok(currencyDto);
    }

    /**
     *  수취국가 금액 제공
     *
     * @param currencyDto
     * @return
     * @throws Exception
     */
    @PostMapping("/getAmountReceived")
    public ResponseEntity getAmountReceived(@RequestBody(required = false) CurrencyDto currencyDto) {
        String targetCurrencyCode = currencyDto.getTargetCurrencyCode();
        BigDecimal remittance = currencyDto.getRemittance();

        if(targetCurrencyCode == null) {
            log.debug("targetCurrencyCode is null !!!");
            return ResponseEntity.badRequest().body("[Request] targetCurrencyCode 가 없습니다.");
        }
        if(remittance == null) {
            log.debug("remittance is null !!!");
            return ResponseEntity.badRequest().body("[Request] remittance 가 없습니다.");
        }

        log.debug("타겟코드 : {} / 송금액 : {}", targetCurrencyCode, remittance.toString());

        try {
            calculateAmountReceived(currencyDto, targetCurrencyCode, remittance);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("calculateAmountReceived 실패 : {}", e.getMessage());
            return ResponseEntity.badRequest().body(currencyDto);
        }

        return ResponseEntity.ok(currencyDto);
    }

    /**
     * 수취국가코드의 환율과  송금액으로 수취국가 금액 계산
     *
     * @param currencyDto
     * @param targetCurrencyCode
     * @param remittance
     * @throws Exception
     */
    private void calculateAmountReceived(CurrencyDto currencyDto, String targetCurrencyCode, BigDecimal remittance) throws Exception {
        BigDecimal currencyRate = currencyService.getCurrencyRateFromMemory(targetCurrencyCode);
        BigDecimal amountReceived = remittance.multiply(currencyRate);

        String formattedRate = StringUtil.decimalFormatter(amountReceived, StringUtil.FMT_GLOBAL_MONEY);
        currencyDto.setAmountReceived(formattedRate);
    }


    /**
     * properties에 담겨있는 source 값을 기준으로 메모리에서 name 을 검색하여 조합
     * @return CurrencyDto
     * @throws Exception
     */
    private CurrencyDto getSourceCodeName() throws Exception {
        CurrencyDto currencyDto = new CurrencyDto();

        String sourceCode = currencylayerProperties.getSource();
        String sourceName = currencyService.getCurrencyCodeNameFromMemory(sourceCode);

        currencyDto.setSourceCode(sourceCode);
        currencyDto.setSourceName(sourceName);

        return currencyDto;
    }

    /**
     * properties에 담겨있는 CurrencyCode 값을 기준으로 메모리에서 name 을 검색하여 조합
     * @return
     * @throws Exception
     */
    private Map<String, String> getCurrencies() throws Exception{
        Map<String, String> resultMap = new HashMap<>();
        String propertiesCurrencies = currencylayerProperties.getCurrencies();
        List<String> currencyCodeList = Arrays.asList(propertiesCurrencies.split(","));
        for(String currencyCode : currencyCodeList) {
            String currencyName = currencyService.getCurrencyCodeNameFromMemory(currencyCode);
            if(StringUtil.isEmptyString(currencyName)) {
                currencyName = "None";
            }
            resultMap.put(currencyCode, currencyName);
        }
        return resultMap;
    }




}
