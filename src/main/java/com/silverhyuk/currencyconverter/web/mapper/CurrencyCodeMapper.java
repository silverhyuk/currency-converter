package com.silverhyuk.currencyconverter.web.mapper;

import com.silverhyuk.currencyconverter.web.dvo.CurrencyCodeVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CurrencyCodeMapper {
    List<CurrencyCodeVo> getCurrencyCodeList(CurrencyCodeVo param);
    CurrencyCodeVo getCurrencyCode(CurrencyCodeVo param);

}
