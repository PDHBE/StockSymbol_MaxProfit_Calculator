package com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StockDto {
    private final LocalDate date;
    private final double lowPrice;
    private final double highPrice;
}
