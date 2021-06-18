package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock;

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
