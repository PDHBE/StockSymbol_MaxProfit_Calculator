package com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.service;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MaxProfitDto {
    private String stockSymbol;
    private LocalDate buyDate;
    private LocalDate sellDate;
    private Double maxProfit;
}
