package com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StockDto {
    private final LocalDate date;
    private final double lowPrice;
    private final double highPrice;

    @Builder
    public StockDto(LocalDate date, double lowPrice, double highPrice) {
        this.date = date;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
    }
}
