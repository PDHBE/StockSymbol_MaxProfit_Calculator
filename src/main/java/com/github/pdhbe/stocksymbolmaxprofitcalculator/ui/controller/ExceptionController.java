package com.github.pdhbe.stocksymbolmaxprofitcalculator.ui.controller;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.model.StockException;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.service.LessThanTwoDaysException;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.service.MaxProfitDto;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.service.MinusProfitException;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.service.ZeroProfitException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(StockException.class)
    public String handleStockException(Model model) {
        model.addAttribute("exceptionMsg", "Invalid Stock Symbol. Input Again.");
        return "index";
    }

    @ExceptionHandler(LessThanTwoDaysException.class)
    public String handleLessThanTwoDaysException(Model model) {
        model.addAttribute(MaxProfitDto.builder().build());
        model.addAttribute("exceptionMsg", "It should be at least two days.");
        return "result";
    }

    @ExceptionHandler(ZeroProfitException.class)
    public String handleZeroProfitException(Model model) {
        model.addAttribute(MaxProfitDto.builder().build());
        model.addAttribute("exceptionMsg", "Cannot make a profit because all prices are same.");
        return "result";
    }

    @ExceptionHandler(MinusProfitException.class)
    public String handleMinusProfitException(Model model) {
        model.addAttribute(MaxProfitDto.builder().build());
        model.addAttribute("exceptionMsg", "Cannot make a profit because the price keeps falling.");
        return "result";
    }
}
