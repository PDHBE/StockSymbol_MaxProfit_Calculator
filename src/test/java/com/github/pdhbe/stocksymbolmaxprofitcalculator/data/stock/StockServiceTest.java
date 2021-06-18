package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockServiceTest {
    @Autowired
    StockService stockService;

    @Test
    void validStockSymbol() {
        String stockSymbol = "AAPL";

        List<StockDto> dailyStockList = stockService.getDailyStockList(stockSymbol);

        assertEquals(180, dailyStockList.size());
    }

    @Test
    void invalidStockSymbol() {
        String stockSymbol = "1234";

        StockException stockException = assertThrows(StockException.class, () -> {
            stockService.getDailyStockList(stockSymbol);
        });

        assertEquals("Invalid Stock Symbol. Input Again.", stockException.getMessage());
    }
}