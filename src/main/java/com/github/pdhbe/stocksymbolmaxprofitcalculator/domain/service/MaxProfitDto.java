package com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
public class MaxProfitDto {
    private final String stockSymbol;
    private final LocalDate buyDate;
    private final LocalDate sellDate;
    private final Double maxProfit;

    @Builder
    public MaxProfitDto(String stockSymbol, LocalDate buyDate, LocalDate sellDate, Double maxProfit) {
        this.stockSymbol = stockSymbol;
        this.buyDate = buyDate;
        this.sellDate = sellDate;
        this.maxProfit = maxProfit;
    }
}
