package com.github.pdhbe.stocksymbolmaxprofitcalculator.application;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.model.StockException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockServiceTest {
    @Autowired
    private StockService stockService;

    @Test
    void invalid_stockSymbol(){
        String invalid_stockSymbol = "1234";
        assertThrows(StockException.class,() -> stockService.getMaxProfit(invalid_stockSymbol));
    }
}