package com.github.pdhbe.stocksymbolmaxprofitcalculator.controller;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.business.MaxProfitProcessor;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dto.StockDto;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi.yahoo.YahooFinanceAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {
    private static final MaxProfitProcessor maxProfitProcessor = new MaxProfitProcessor();

    @GetMapping("/maxProfit")
    public String getMaxProfit(@RequestParam String stockSymbol, Model model) {
        YahooFinanceAPI yahooFinanceAPI = new YahooFinanceAPI();

        List<StockDto> stockDtoList;
        try {
            stockDtoList = yahooFinanceAPI.getDailyStockList(stockSymbol);
        } catch (Exception exception) {
            model.addAttribute("errMsg", exception.getMessage());
            return "index";
        }


        try {
            maxProfitProcessor.setDailyStockInfoList(stockDtoList);
        } catch (Exception exception) {
            model.addAttribute("errMsg",exception.getMessage());
            return "result";
        }

        model.addAttribute("result", maxProfitProcessor.toString());
        model.addAttribute("newLineChar", '\n');
        return "result";
    }
}