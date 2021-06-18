package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi.yahoo;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.source.yahoo.YahooFinanceAPI;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock.StockDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YahooFinanceAPITest {
    @Test
    void yahooAPI(){
        YahooFinanceAPI yahooFinanceAPI = new YahooFinanceAPI();
        String stockSymbol = "AAPL";

        List<StockDto> dailyStockList = yahooFinanceAPI.getDailyStockList(stockSymbol);

        assertEquals(180, dailyStockList.size());
    }
}