package com.github.pdhbe.stocksymbolmaxprofitcalculator.data;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class dailyStockInfo {
    private final LocalDate date;
    private final double lowPrice;
    private final double highPrice;
}
