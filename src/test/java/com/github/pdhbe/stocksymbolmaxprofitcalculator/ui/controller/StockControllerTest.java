package com.github.pdhbe.stocksymbolmaxprofitcalculator.ui.controller;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.application.StockService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StockControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void valid_StockSymbol() throws Exception {
        String stockSymbol = "AAPL";
        mockMvc.perform(get("/maxProfit")
                .param("stockSymbol", stockSymbol))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attributeExists("maxProfitDto"))
                .andExpect(model().attributeDoesNotExist("exceptionMsg"));
    }

    @Test
    void invalid_StockSymbol() throws Exception {
        String stockSymbol = "1234";
        mockMvc.perform(get("/maxProfit")
                .param("stockSymbol", stockSymbol))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("exceptionMsg", "Invalid Stock Symbol. Input Again."));
    }
}