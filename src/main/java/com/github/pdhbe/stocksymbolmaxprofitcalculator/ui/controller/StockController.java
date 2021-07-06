package com.github.pdhbe.stocksymbolmaxprofitcalculator.ui.controller;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.application.StockService;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.model.StockException;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.service.MaxProfitDto;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.service.MaxProfitException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/maxProfit")
    public String getMaxProfit(@RequestParam String stockSymbol, Model model) {
        try {
            MaxProfitDto maxProfit = stockService.getMaxProfit(stockSymbol);
            model.addAttribute(maxProfit);
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
