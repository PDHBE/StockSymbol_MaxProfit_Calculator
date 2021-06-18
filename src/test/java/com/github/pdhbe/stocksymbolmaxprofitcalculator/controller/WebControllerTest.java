package com.github.pdhbe.stocksymbolmaxprofitcalculator.controller;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock.StockDto;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock.StockService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class WebControllerTest {
    @Autowired
    MockMvc mockMvc;

    @SpyBean
    StockService stockService;

    @Test
    void validStockSymbolAndMaxProfit() throws Exception {
        String stockSymbol = "AAPL";
        mockMvc.perform(get("/maxProfit")
        .param("stockSymbol",stockSymbol))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attributeExists("maxProfitDto"))
                .andExpect(model().attributeDoesNotExist("exceptionMsg"));
    }

    @Test
    void invalidStockSymbol() throws Exception {
        String stockSymbol = "1234";

        mockMvc.perform(get("/maxProfit")
                .param("stockSymbol", stockSymbol))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("exceptionMsg", "Invalid Stock Symbol. Input Again."));
    }

    @Test
    void lessThanTwoDays() throws Exception {
        String validStockSymbol = "AAPL";
        List<StockDto> stockDtoList = new ArrayList<>();
        stockDtoList.add(
                StockDto.builder()
                        .date(LocalDate.now())
                        .lowPrice(0)
                        .highPrice(10)
                        .build()
        );
        Mockito.when(stockService.getDailyStockList(validStockSymbol)).thenReturn(stockDtoList);

        mockMvc.perform(get("/maxProfit")
                .param("stockSymbol", validStockSymbol))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("exceptionMsg", "It should be at least two days."));
    }

    @Test
    void allPricesSame() throws Exception {
        String validStockSymbol = "AAPL";
        List<StockDto> stockDtoList = new ArrayList<>();
        stockDtoList.add(
                StockDto.builder()
                        .date(LocalDate.now().minusDays(2))
                        .lowPrice(10)
                        .highPrice(10)
                        .build()
        );
        stockDtoList.add(
                StockDto.builder()
                        .date(LocalDate.now().minusDays(1))
                        .lowPrice(10)
                        .highPrice(10)
                        .build()
        );
        stockDtoList.add(
                StockDto.builder()
                        .date(LocalDate.now())
                        .lowPrice(10)
                        .highPrice(10)
                        .build()
        );
        Mockito.when(stockService.getDailyStockList(validStockSymbol)).thenReturn(stockDtoList);

        mockMvc.perform(get("/maxProfit")
                .param("stockSymbol", validStockSymbol))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("exceptionMsg", "Cannot make a profit because all prices are same."));
    }

    @Test
    void pricesKeepFalling() throws Exception {
        String validStockSymbol = "AAPL";
        List<StockDto> stockDtoList = new ArrayList<>();
        stockDtoList.add(
                StockDto.builder()
                        .date(LocalDate.now().minusDays(2))
                        .lowPrice(10)
                        .highPrice(10)
                        .build()
        );
        stockDtoList.add(
                StockDto.builder()
                        .date(LocalDate.now().minusDays(1))
                        .lowPrice(9)
                        .highPrice(9)
                        .build()
        );
        stockDtoList.add(
                StockDto.builder()
                        .date(LocalDate.now())
                        .lowPrice(8)
                        .highPrice(8)
                        .build()
        );
        Mockito.when(stockService.getDailyStockList(validStockSymbol)).thenReturn(stockDtoList);

        mockMvc.perform(get("/maxProfit")
                .param("stockSymbol", validStockSymbol))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("exceptionMsg", "Cannot make a profit because the price keeps falling."));
    }
}