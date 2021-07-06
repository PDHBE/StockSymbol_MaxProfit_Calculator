package com.github.pdhbe.stocksymbolmaxprofitcalculator.application;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.model.StockDto;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.service.MaxProfitCalculationService;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.service.MaxProfitDto;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.infra.twelve.TwelveDao;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.infra.yahoo.YahooDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final YahooDao yahooDao;
    private final TwelveDao twelveDao;
    private final MaxProfitCalculationService maxProfitCalculationService;

    public MaxProfitDto getMaxProfit(String stockSymbol) {
//        List<StockDto> stocks = yahooDao.stocks(stockSymbol);
        List<StockDto> stocks = twelveDao.stocks(stockSymbol);
        return maxProfitCalculationService.calculate(stockSymbol, stocks);
    }
}
