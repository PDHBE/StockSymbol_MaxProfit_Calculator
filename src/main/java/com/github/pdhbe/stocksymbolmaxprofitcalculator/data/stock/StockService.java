package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.source.twelve.TwelveDataAPI;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.source.yahoo.YahooFinanceAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService implements StockDtoFetcher {
    private final YahooFinanceAPI yahooFinanceAPI;
    private final TwelveDataAPI twelveDataAPI;

    @Override
    public List<StockDto> getDailyStockList(String stockSymbol) throws StockException {
        return yahooFinanceAPI.getDailyStockList(stockSymbol);
//        return twelveDataAPI.getDailyStockList(stockSymbol);
    }
}
