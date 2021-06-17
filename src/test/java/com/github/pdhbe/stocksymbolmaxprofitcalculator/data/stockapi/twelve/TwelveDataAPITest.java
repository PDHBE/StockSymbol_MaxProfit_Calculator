package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi.twelve;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dto.StockDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TwelveDataAPITest {
    @Test
    void twelveAPI(){
        TwelveDataAPI twelveDataAPI = new TwelveDataAPI();
        String stockSymbol = "AAPL";

        List<StockDto> dailyStockList = twelveDataAPI.getDailyStockList(stockSymbol);

        assertEquals(180,dailyStockList.size());
    }
}