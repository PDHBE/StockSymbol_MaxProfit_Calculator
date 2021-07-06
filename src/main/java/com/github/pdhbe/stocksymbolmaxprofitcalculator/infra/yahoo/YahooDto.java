package com.github.pdhbe.stocksymbolmaxprofitcalculator.infra.yahoo;

import lombok.Data;

@Data
public class YahooDto {
    private Long date;
    private double low;
    private double high;
}
