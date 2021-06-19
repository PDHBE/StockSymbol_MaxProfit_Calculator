package com.github.pdhbe.stocksymbolmaxprofitcalculator.controller;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.business.MaxProfitCalculator;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.business.MaxProfitDto;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.business.MaxProfitException;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock.StockDto;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock.StockException;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final StockService stockService;
    private final MaxProfitCalculator maxProfitCalculator;

    @GetMapping("/maxProfit")
    public String getMaxProfit(@RequestParam String stockSymbol, Model model) {
        try {
            List<StockDto> dailyStockList = stockService.getDailyStockList(stockSymbol);
            MaxProfitDto maxProfitDto = maxProfitCalculator.calculate(stockSymbol,dailyStockList);
            model.addAttribute(maxProfitDto);
            return "result";
        } catch (StockException stockException) {
            model.addAttribute("exceptionMsg", stockException.getMessage());
            return "index";
        } catch (MaxProfitException maxProfitException) {
            model.addAttribute(MaxProfitDto.builder().build());
            model.addAttribute("exceptionMsg", maxProfitException.getMessage());
            return "result";
        }
    }
}