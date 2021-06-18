package com.github.pdhbe.stocksymbolmaxprofitcalculator.business;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MaxProfitDto {
    private LocalDate buyDate;
    private LocalDate sellDate;
    private Double maxProfit;
}
