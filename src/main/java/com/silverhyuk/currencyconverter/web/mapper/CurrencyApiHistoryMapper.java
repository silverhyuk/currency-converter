package com.silverhyuk.currencyconverter.web.mapper;

import com.silverhyuk.currencyconverter.web.dvo.CurrencyApiHistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CurrencyApiHistoryMapper {
    List<CurrencyApiHistoryVo> getCurrencyApiHistoryList(CurrencyApiHistoryVo param);
    int setCurrencyApiHistoryInfo(CurrencyApiHistoryVo param);
}
